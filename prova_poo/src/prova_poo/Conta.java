package prova_poo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String numero;
	float saldo;
	LocalDateTime dateAbertura;
	boolean status;
	String proprietario;
	
	
	private static ArrayList<Conta> contasCadastradas = new ArrayList<>();
	private ArrayList<Transacao> transacoes = new ArrayList<>();
	
	public Conta() {
		this.numero = !verificarNumeroExistente(gerarNumeroAleatorio()) ? gerarNumeroAleatorio() : gerarNumeroAleatorio();
		this.saldo = 0f;
		this.dateAbertura = LocalDateTime.now();
		this.status = true;
		this.proprietario = "";
		contasCadastradas.add(this);
		salvarEmContasCadastradas();
		
	}
	public Conta(String num) {
		this.numero = num;
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public String getProprietario() {
		return this.proprietario;
	}
	
	public float getSaldo() {
		return this.saldo;
		
	}
	
    private String gerarNumeroAleatorio() {
        Random random = new Random();
        int numeroAleatorio = 100000 + random.nextInt(900000); 
        return Integer.toString(numeroAleatorio);
    }
    
    private boolean verificarNumeroExistente(String num) {
    	if(localizarContaPorNumero(num) == null) {    		
    		return false;
    	} else { // medo    		
    		return true;
    	}
    }
	
	public void depositar( float quantia) 
	{
		lerContasCadastradas();
		if(status && quantia > 0) {
			saldo+=quantia;
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.CREDITO));
			salvarEmExtrato();
			salvarEmContasCadastradas();
		} else {
			System.out.println("A operação não pode ser executada.");
		}
	}
	
	public void sacar (float quantia)
	{
		lerContasCadastradas();
		if(status && quantia > 0 && saldo >= quantia) {
			saldo -= quantia;
			transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.DEBITO));
			salvarEmExtrato();
			salvarEmContasCadastradas();
		} else {
			System.out.println("A operação não pode ser executada.");
		}
	}
	
	
	public void transferir (float quantia, Conta destino)
	{
		lerContasCadastradas();
		if(this.status && destino.status && quantia > 0 && quantia <= this.saldo) {
			destino.saldo += quantia;
			this.saldo-= quantia;
			
			this.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_DEBITO, destino));
			salvarEmExtrato();
			destino.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_CREDITO, this));
			destino.salvarEmExtrato();
			salvarEmContasCadastradas();
		} else {
			System.out.println("A operação não pode ser executada.");
		}
	}
	
	public void transferir (float quantia, Conta origem, Conta destino)
	{
		lerContasCadastradas();
		if(origem.status && destino.status && quantia > 0 && quantia <= origem.saldo) {
			destino.saldo += quantia;
			origem.saldo-= quantia;
			
			origem.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_DEBITO, destino));
			destino.transacoes.add(new Transacao(quantia, LocalDateTime.now(), TipoTransacao.TRANSFERENCIA_CREDITO, origem));
			salvarEmContasCadastradas();
		} else {
			System.out.println("A operação não pode ser executada.");
		}
	}
	
	public void extrato(int year, Month month) {
		for(Transacao t : transacoes) {
			if(t.dateTransacao.getYear() == year && t.dateTransacao.getMonth() == month) {
				System.out.println(t);
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Conta other = (Conta) obj;
		return Objects.equals(numero, other.numero);
	}


	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", saldo=" + saldo + ", dateAbertura=" + dateAbertura + ", status=" + status
				+ "]";
	}

	public String toStringPersonalizado() {
		return "Conta "
				+ "---------------------------------------" + "\n"
				+ "\n Numero da Conta = " + numero + "\n"
				+ "\n Saldo = " + saldo + "\n"
				+ "\n Data de Abertura = " + dateAbertura + "\n" 
				+ "\n status = " + status + "\n"
				+ "----------------------------------------------";
	}
	
	public static Conta localizarContaPorNumero(String num) 
	{
		
		    lerContasCadastradas();
		    
		    for (Conta conta : contasCadastradas) {
		        if (conta.getNumero().equals(num)) {
		            return conta;
		        }
		    }
		    
		    return null;
	}
	
	public static String getTotalContas() {
		return String.valueOf(contasCadastradas.size());
	}

    
	public void salvarEmExtrato() {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Extrato_" + this.getNumero()))) {
	        oos.writeObject(this.transacoes);
	    } catch (IOException e) {
	        System.err.println("Erro ao salvar Extrato no arquivo: " + e.getMessage());
	    }
	}

	@SuppressWarnings("unchecked")
	public void lerExtrato() {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Extrato_" + this.getNumero()))) {
	        // A leitura diretamente para o ArrayList é apropriada neste caso
	        this.transacoes = (ArrayList<Transacao>) ois.readObject();
	    } catch (ClassNotFoundException | IOException e) {
	        System.err.println("Erro ao ler Extrato do arquivo: " + e.getMessage());
	    }
	}
	    
	
	
	
	public String imprimirExtrato() {
		lerExtrato();
	    StringBuilder extrato = new StringBuilder();

	    for (Transacao transacao : this.transacoes) {
	        extrato.append(transacao.toString()).append("\n");
	    }

	    return extrato.toString();
	
	}
	
	
    public static void salvarEmContasCadastradas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ContasCadastradas"))) {
            oos.writeObject(contasCadastradas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar contas cadastradas no arquivo: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void lerContasCadastradas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ContasCadastradas"))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                contasCadastradas = (ArrayList<Conta>) obj;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Erro ao ler contas cadastradas do arquivo: " + e.getMessage());
        }
    }
        
    public static void imprimirContasCadastradas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ContasCadastradas"))) {
            ArrayList<?> c = (ArrayList<?>) ois.readObject();
            System.out.println(c.toString());
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Erro ao ler contas cadastradas do arquivo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {

		imprimirContasCadastradas();

	}
}
	
