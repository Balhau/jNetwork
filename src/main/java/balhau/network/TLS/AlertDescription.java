package balhau.network.TLS;
/**
 * Enumerado que representa o tipo de alerta
 * @author balhau
 *
 */
public enum AlertDescription {
	close_notify(0),
	unexpected_message(10),
	bad_record_mac(20),
	decryption_failed(21),
	record_overflow(22),
	decompression_failure(30),
	handshake_failure(40),
	bad_certificate(42),
	unsupported_certificate(43),
	certificate_revoked(44),
	certificate_expired(45),
	certificate_unknown(46),
	illegal_parameter(47),
	unknown_ca(48),
	access_denied(49),
	decode_error(50),
	decrypt_error(51),
	export_restriction(60),
	protocol_version(70),
	insufficient_security(71),
	internal_error(80),
	user_canceled(90),
	no_regenotiation(100),
	other(255);
	
	private final int val;
	AlertDescription(int v) {
		this.val=v;
	}
	public int val(){return val;}
}
