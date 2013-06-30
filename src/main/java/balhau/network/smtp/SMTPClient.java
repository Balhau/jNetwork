/**
 * Pacote que contem rotinas para implementação do protocolo de email SMTP
 */
package balhau.network.smtp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Random;

import balhau.crypto.coding.base64;
import balhau.metastruct.Lista;
import balhau.metastruct.Par;
import balhau.network.Message;
import balhau.network.MimeTypes;
import balhau.utils.FileUtils;
import balhau.utils.Log;

/**
 * Classe que permite o envio de emails para servidores SMTP
 * @author balhau
 *
 */
public class SMTPClient {
	public static String HELO="HELO";
	public static String RCTPTO="RCPT TO:";
	public static String MAILFROM="MAIL FROM:";
	private static String DEF_ZONE_CODE="+0100";
	private static String MIME_MULTIPART="multipart/mixed;";
	private static String MIME_ALTERNATIVE="multipart/alternative";
	private static String MULTIPART_LABEL="This is a multi-part message in MIME format.";
	private static String[] months={"Jan" , "Feb" , "Mar" , "Apr" ,
        "May" , "Jun" , "Jul" , "Aug" ,
        "Sep" , "Oct" , "Nov" , "Dec"};
	private static String[] days={ "Mon" , "Tue" , "Wed" , "Thu" ,
        "Fri" , "Sat" , "Sun"};
	private int porta;
	private Socket conn;
	private BufferedReader br;
	private PrintWriter pwr;
	private Lista<Par<String,String>> recp;
	private Lista<Par<String, String>> ccrecp;
	private Lista<Par<String,String>> bccrecp;
	private String host;
	private base64 b64;
	private Par<String,String> sender;
	private String user;
	private String passw;
	private String corpo;
	private String assunto;
	private String ContentType;
	private String boundMultipart;
	private String corpoHtml;
	private boolean hasHTML;
	private static int BOUND_LENGTH=20;
	private String charset;
	private static String USER_AGENT="Balhau Mailer";
	private Lista<Par<String,String>> attachments;
	
	/**
	 * Construtor do cliente de Simple Mail Transfer Protocol (SMTP
	 * @param porta {@link Integer} porta de escuta do servidor.
	 */
	public SMTPClient(String host,int porta){
		this.porta=porta;
		this.host=host;
		sender=new Par<String, String>("","");
		this.LimpaEnderecos();
		this.attachments=new Lista<Par<String,String>>();
		ContentType="text/plain";
		hasHTML=false;
		charset="UTF-8";
	}
	/**
	 * Especifica informações de conta.
	 * @param email {@link String} Conta de email.
	 * @param passw {@link String} Password associado à conta.
	 */
	public void setAccount(String email,String passw){
		this.user=email;
		this.passw=passw;
	}
	/**
	 * Especifica o valor campo assunto do email
	 * @param subject {@link String} Assunto
	 */
	public void setSubject(String subject){
		this.assunto=subject;
	}
	
	/**
	 * Especifica o conteúdo do email
	 * @param body {@link String} Conteúdo de email
	 */
	public void setBody(String body){
		this.corpo=body;
	}
	
	/**
	 * Especifica o conteúdo do email em formato HTML
	 * @param htmlBody {@link String} Conteúdo do email em formato HTML
	 */
	public void setHtmlBody(String htmlBody){
		if(htmlBody.equals(""))
			hasHTML=false;
		else
			hasHTML=true;
		corpoHtml=htmlBody;
	}
	/**
	 * Método que define as propriedades do remetente
	 * @param nome {@link String} nome do remetente
	 * @param email {@link String} email do remetente
	 */
	public void setSender(String nome,String email){
		sender.chave=nome;
		sender.valor=email;
	}
	/**
	 * Método que especifica o tipo de codificação associada ao conteúdo. 
	 * @param bool {@link Boolean} verdadeiro caso se esteja a tratar de informação em formato html, 
	 * falso caso o formato seja do tipo plain text
	 */
	
	public void setCharSet(String charset){
		this.charset=charset;
	}
	/**
	 * Método que adiciona um endereço à lista de contactos que irão receber o email
	 * @param nome {@link String} nome associado ao email
	 * @param email {@link String} email
	 */
	public void AddEndereco(String nome, String email){
		this.recp.addValue(new Par<String, String>(nome, email.trim()));
	}
	
	/**
	 * Adiciona um endereço de email à lista CC
	 * @param nome {@link String} Alias para o email
	 * @param email {@link String} Nome do email
	 */
	public void AddCCEndereco(String nome, String email){
		this.ccrecp.addValue(new Par<String, String>(nome, email.trim()));
	}
	
	/**
	 * Adiciona um endereço de email à lista BCC
	 * @param nome {@link String} Alias para o email
	 * @param email {@link String} Nome do email
	 */
	public void AddBccEndereco(String nome,String email){
		this.bccrecp.addValue(new Par<String, String>(nome, email.trim()));
	}
	/**
	 * Método que efectua a limpeza nos endereços a enviar
	 */
	public void LimpaEnderecos(){
		this.recp=new Lista<Par<String,String>>();
		this.bccrecp=new Lista<Par<String,String>>();
		this.ccrecp=new Lista<Par<String,String>>();
	}
	/**
	 * Adiciona um anexo à lista de anexos do email
	 * @param name {@link String} Nome do ficheiro
	 * @param filepath {@link String} Caminho do ficheiro em anexo
	 */
	public void AddAttachment(String name,String filepath){
		this.attachments.addValue(new Par<String, String>(name, filepath));
	}
	/**
	 * Método que efectua a construção da data para o protocolo smtp
	 * @param date {@link Date} Data a formatar
	 * @return {@link String} Representa a data contida no objecto Date.
	 */
	private static String getMailDate(Date date){
		int year=1900+date.getYear();
		return days[date.getDay()]+", "+date.getDate()+" "+months[date.getMonth()]+" "+year+" "
		+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+" "+DEF_ZONE_CODE;
	}
	/**
	 * Método responsável por efectuar o envio do email
	 * @return {@link boolean} Verdadeiro caso o email tenha sido enviado com sucesso, falso caso contrário
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public boolean sendMail() throws UnknownHostException, IOException{
		processaAutenticacao();
		processRCPTTo();
		processaHeader();
		processaMail();
		processaEnd();
		return true;
	}
	/**
	 * Rotina que finaliza o processo de comunicações com o servidor SMTP 
	 * @throws IOException
	 */
	private void processaEnd() throws IOException{
		pwr.write("QUIT"+Message.CRLF);
		pwr.flush();
		String msg=getMailSMTPMessage();	
		conn.close();
	}
	/**
	 * Método que efectua autenticação com o servidor SMTP
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void processaAutenticacao() throws UnknownHostException, IOException{
		this.conn=new Socket(host,porta);
		br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		pwr=new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
		b64=new base64();
		String msg;
		msg=getMailSMTPMessage();
		pwr.write("EHLO balhau.com"+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		pwr.write("AUTH LOGIN"+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		b64.setString(user);
		pwr.write(b64.encode()+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		b64.setString(passw);
		pwr.write(b64.encode()+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		pwr.write("MAIL FROM: <"+sender.valor+">"+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		
	}
	
	/**
	 * Método que cria uma sequência de caracterres para servir de fronteira entre os vários conteúdos presentes
	 * no email.
	 */
	private void generateBoundary(){
		Random rn=new Random();
		boundMultipart="";
		String aux="";
		int num;
		for(int i=0;i<BOUND_LENGTH/2;i++){
			boundMultipart+="-";
		}
		for(int i=0;i<BOUND_LENGTH;i++){
			num=Math.abs(rn.nextInt())%10;
			aux+=num;
		}
		boundMultipart+=aux;
	}
	/**
	 * Rotina que efectua a construção do corpo do email.
	 * @throws IOException
	 */
	private void processaMail() throws IOException{
		@SuppressWarnings("unused")
		String msg="";
		if(attachments.getNumElementos()>0){
			generateBoundary();
			pwr.write("Content-Type: "+MIME_MULTIPART+"; boundary="+boundMultipart+Message.CRLF);
			pwr.write("Message Multipart in MIME format"+Message.CRLF);
			pwr.write("--"+boundMultipart+Message.CRLF);
			pwr.write("Content-Type: "+ContentType+"; charset="+charset+"; format=flowed"+Message.CRLF+"Content-Transfer-Encoding: 8bit"+Message.CRLF+Message.CRLF);
			pwr.write(corpo+Message.CRLF+Message.CRLF);
			pwr.write("--"+boundMultipart+Message.CRLF);
			String mtype;
			String tenc;
			Par <String,String> item;
			for(int i=0;i<attachments.getNumElementos();i++){
				item=attachments.getValor(i);
				mtype=MimeTypes.getTypeFromExtension(FileUtils.getExtension(item.valor));
				if(mtype.equals("text/html") || mtype.endsWith("text/plain"))
					tenc="8bit";
				else
					tenc="base64";
				pwr.write("Content-Type: "+mtype+"; ");
				pwr.write("name=\""+item.chave+"\""+Message.CRLF);
				pwr.write("Content-Transfer-Encoding: "+tenc+Message.CRLF);
				pwr.write("Content-Disposition: attachment; ");
				pwr.write("filename=\""+item.chave+"\""+Message.CRLF+Message.CRLF);
				
				if(tenc.equals("base64")){
					printB64File(item.valor);
				}else{
					BufferedReader in= new BufferedReader(new FileReader(item.valor));
					String temp;
					while((temp=in.readLine())!=null){
						pwr.write(temp+"\n");
					}
				}
				pwr.write("--"+boundMultipart+Message.CRLF);
			}
		}
		if(!hasHTML){
			pwr.write("Content-Type: text/plain; charset="+charset+";format=flowed"+Message.CRLF+"Content-Transfer-Encoding: 8bit"+Message.CRLF+Message.CRLF);
			pwr.write(corpo+Message.CRLF+Message.CRLF);
		}
		else{
			processaHTMLMail();
		}
		pwr.write(Message.CRLF+"."+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
	}
	
	/**
	 * Rotina que efectua a conversão do conteúdo dos ficheiros para a sua representação {@link base64}
	 * @param path {@link String} Caminho do ficheiro
	 * @throws IOException
	 */
	private void printB64File(String path) throws IOException{
		InputStream fs=new FileInputStream(path);
		int step=3;
		long off=3;
		byte[] block;
		byte[] aux;
		boolean avaiable=true;
		int pos;
		int val;
		while(avaiable){
			pos=0;
			aux=new byte[step];
			while(pos<3){
				if((val=fs.read())==-1){
					avaiable=false;
					break;
				}
				aux[pos]=(byte)val;
				pos++;
			}
			block=new byte[pos];
			for(int j=0;j<pos;j++){
				block[j]=aux[j];
			}
			pwr.write(b64.encodeBlock(block));
		}
	}
	
	/**
	 * Rotina que efectua o processamento do corpo do email para conteúdo HTML
	 */
	private void processaHTMLMail(){
		generateBoundary();
		pwr.write("Content-Type: "+MIME_ALTERNATIVE+"; ");
		pwr.write("boundary=\""+boundMultipart+"\""+Message.CRLF+Message.CRLF);
		pwr.write(MULTIPART_LABEL+Message.CRLF);
		pwr.write("--"+boundMultipart+Message.CRLF);
		pwr.write("Content-Type: "+ContentType+"; charset="+charset+";format=flowed"+Message.CRLF+"MIME-Version: 1.0"+Message.CRLF+"Content-Transfer-Encoding: 8bit"+Message.CRLF+Message.CRLF);
		pwr.write(corpo+Message.CRLF+Message.CRLF);
		pwr.write("--"+boundMultipart+Message.CRLF);
		pwr.write("Content-Type: text/html; charset="+charset+Message.CRLF+"Content-Transfer-Encoding: 7bit"+Message.CRLF+Message.CRLF);
		pwr.write(corpoHtml+Message.CRLF);
		pwr.write("--"+boundMultipart+Message.CRLF);
	}
	/**
	 * Rotina que efectua o processamento dos cabeçalhos de email
	 * @throws IOException
	 */
	private void processaHeader() throws IOException{
		Par<String,String> it;
		String msg;
		pwr.write("Data"+Message.CRLF);
		pwr.flush();
		msg=getMailSMTPMessage();
		pwr.write("Date: "+getMailDate(new Date())+Message.CRLF);
		pwr.write("From: "+sender.chave+" <"+sender.valor+">"+Message.CRLF);
		pwr.write("User-Agent: "+USER_AGENT+Message.CRLF);
		pwr.write("MIME-Version: 1.0"+Message.CRLF);
		pwr.write("To: ");
		for(int i=0;i<recp.getNumElementos();i++){
			it=recp.getValor(i);
			if(i!=0)
				pwr.write(",");
			pwr.write(it.valor);
		}
		pwr.write(Message.CRLF);
		if(ccrecp.getNumElementos()>0){
			pwr.write("CC: ");
		}
		for(int i=0;i<ccrecp.getNumElementos();i++){
			it=ccrecp.getValor(i);
			if(i!=0)
				pwr.write(",");
			pwr.write(it.valor);
		}
		if(ccrecp.getNumElementos()>0)
			pwr.write(Message.CRLF);
		pwr.write("Subject: "+assunto+Message.CRLF);
	}
	/**
	 * Rotina que efectua o processamento da lista RCPT TO
	 * @throws IOException
	 */
	private void processRCPTTo() throws IOException{
		String msg;
		for(int i=0;i<recp.getNumElementos();i++){
			pwr.write("RCPT TO: <"+recp.getValor(i).valor+">"+Message.CRLF);
			pwr.flush();
			msg=getMailSMTPMessage();
		}
		for(int i=0;i<bccrecp.getNumElementos();i++){
			pwr.write("RCPT TO: <"+bccrecp.getValor(i).valor+">"+Message.CRLF);
			pwr.flush();
			msg=getMailSMTPMessage();
		}
	}
	/**
	 * Rotina que efecua a leitura de uma mensagem enviada do servidor para o cliente
	 * @return {@link String} Mensagem enviada pelo servidor
	 * @throws IOException
	 */
	public String getMailSMTPMessage() throws IOException{
		StringBuilder sb=new StringBuilder("");
		String aux="";
		Log.log("START MESSAGE");
		int ch;
		while((ch=br.read())!=-1 && br.ready())
			sb.append((char)ch);
		sb.append(aux+Message.CRLF);
		Log.log("END MESSAGE");
		return sb.toString();
	}
}
