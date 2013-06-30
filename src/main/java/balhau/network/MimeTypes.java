package balhau.network;

import balhau.metastruct.Lista;
import balhau.metastruct.Par;

/**
 * Tipos de dados especificados no formato MIME. Útil para a implementação de ferramentas que sejam
 * responsáveis por lidar com dados. Os {@link MimeTypes} identificam o tipo de conteúdo presente
 * nos dados 
 * @author balhau
 *
 */
public class MimeTypes {
	private static String types[][]={
		//Aplicacoes
		{"atom,xml","application/atom"},
		{"json","application/json"},
		{"js","application/js"},
		{"ogg","application/ogg"},
		{"doc","application/msword"},
		{"xls","application/vnd.ms-excel"},
		{"ppt","application/vnd.ms-powerpoint"},
		{"pdf","application/pdf"},
		{"ps","application/postscript"},
		{"woff","application/woff"},
		{"xhtml,xht","application/xhtml+xml"},
		{"dtd","application/dtd"},
		{"zip","application/zip"},
		{"gz","application/x-gzip"},
		//Audio
		{"au,snd","audio/basic"},
		{"mid,rmi","audio/mid"},
		{"mp3","audio/mpeg"},
		{"aif,aifc,aiff","audio/x-aiff"},
		{"m3u","audio/x-mpegurl"},
		{"ra,ram","audio/x-pn-realaudio"},
		{"wav","audio/x-wav"},
		//Imagem
		{"bmp","image/bmp"},
		{"cod","image/cis-cod"},
		{"gif","image/gif"},
		{"ief","image/ief"},
		{"jpe,jpg,jpeg","image/jpeg"},
		{"jfif","image/pipeg"},
		{"png","image/png"},
		{"svg","image/svg+xml"},
		{"tiff,tif","image/tiff"},
		{"ras","image/x-cmu-raster"},
		{"cmx","image/x-cmx"},
		{"ico","image/x-ico"},
		{"pnm","image/x-portable-anymap"},
		{"pbm","image/x-portable-bitmap"},
		{"pgm","image/x-portable-grayscale"},
		{"ppm","image/x-portable-pixmap"},
		{"rgb","image/x-rgb"},
		{"xbm","image/x-xbitmap"},
		{"xpm","image/x-xpixmap"},
		{"xwd","image/x-xwindowdump"},
		//Mensagem
		{"mht,mhtl,nws","message/rfc822"},
		//Texto
		{"css","text/css"},
		{"323","text/h323"},
		{"htm,html,stm","text/html"},
		{"uls","text/iuls"},
		{"bas,c,h,txt","text/plain"},
		{"rxt","text/richtext"},
		{"sct","text/scriptlet"},
		{"tsv","text/tab-separated-values"},
		{"htt","text/webviewhtml"},
		{"htc","text/x-component"},
		{"etx","text/x-setext"},
		{"vcf","text/x-vcard"},
		//Video
		{"mp2,mpa,mpe,mpeg,mpg,mpv2","video/mpeg"},
		{"mov,qt","video/quicktime"},
		{"lsf,lsx","video/x-la-asf"},
		{"asf,asr,asx","video/x-ms-asf"},
		{"avi","video/x-msvideo"},
		{"movie","video/x-sgi-movie"},
		//X
		{"flr,vrml,wrl,wrz,xaf,xof","x-world/x-vrml"}
		};
	
	/**
	 * Método estático que devolve o MimeType em função da extensão de um determinado ficheiro
	 * @param ext {@link String} Extensão do ficheiro
	 * @return {@link String} Mime-type correspondente ao ficheiro
	 */
	public static String getTypeFromExtension(String ext){
		String e=ext.toLowerCase();
		String arr[];
		for(int i=0;i<types.length;i++){
			arr=types[i][0].split(",");
			for(int j=0;j<arr.length;j++){
				if(arr[j].equals(e))
					return types[i][1];
			}
		}
		return ""; 
	}
}
