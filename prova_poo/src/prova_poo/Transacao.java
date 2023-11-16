package prova_poo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;



public class Transacao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4512721615765495417L;
	long Id;
	float valor;
	LocalDateTime dateTransacao;
	TipoTransacao tipo;
	Conta contaTransacao;

	public Transacao(float valor, LocalDateTime dateTransacao, TipoTransacao tipo)
	{
		this.Id	= ((long)Math.random()*100000L);
		this.valor = valor;
		this.dateTransacao = dateTransacao;
		this.tipo = tipo;
	}
	public Transacao(float valor, LocalDateTime dateTransacao, TipoTransacao tipo, Conta contaTransacao)
	{
		this.Id	= ((long)Math.random()*100000L);
		this.valor = valor;
		this.dateTransacao = dateTransacao;
		this.tipo = tipo;
		this.contaTransacao = contaTransacao;
	}
	@Override
	public String toString() {
		return "Transacao [Id=" + Id + ", valor=" + valor + ", dateTransacao=" + dateTransacao + ", tipo=" + tipo
				+ ", contaTransacao=" + contaTransacao + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transacao other = (Transacao) obj;
		return Id == other.Id;
	}
	
	

}
