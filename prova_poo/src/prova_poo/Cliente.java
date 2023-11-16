package prova_poo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String cpf, nome; // atributos do Cliente.
	ArrayList<Conta> contas = new ArrayList<>(); // contas pertencentes ao cliente.
	
	private static int totalClientes = 0; // atributo de classe.
	
	public Cliente(String cpf, String nome) {
		if(ClientesCadastrados.procurarClientePorCPF(cpf) == null) {			
			this.cpf = cpf;
			this.nome = nome;
			totalClientes++;
			ClientesCadastrados.addCliente(this);
		} 
	}
	
	public boolean possuiContas() {
		lerContasDoCliente();
		if(contas.isEmpty()) {
			return false;
		}
			return true;
	}
	
	public String getCpf() 
	{
		return this.cpf;
	}
	
	public String getNome() 
	{
		return this.nome;
	}
	
	public static int getTotalClientes() 
	{
		return totalClientes;
	}

	public void addConta(Cliente proprietario, Conta conta) {
		lerContasDoCliente();
		Conta.lerContasCadastradas();
	    if (!proprietario.contas.contains(conta)) {
	    	conta.proprietario = proprietario.nome;  
	    	proprietario.contas.add(conta);
	        salvarEmContasDoCliente();
	        Conta.salvarEmContasCadastradas();
	    } else {
	        System.out.println("Ocorreu um erro.");
	    }
	}
	
	public void deleteConta(String num) {
	    lerContasDoCliente();
	    Conta.lerContasCadastradas();
	    Conta contaParaRemover = localizarContaClientePorNumero(num);

	    if (contaParaRemover != null) {
	        contas.remove(contaParaRemover);
	        salvarEmContasDoCliente();
	        Conta.salvarEmContasCadastradas();
	    }
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}
	
	public String getContas() {
	    lerContasDoCliente();  // le arquivo e passa para o arraylist
	    if (!this.possuiContas()) {
	        return "não possui.";
	    }

	    StringBuilder contasFormatadas = new StringBuilder("Contas:\n");

	    for (Conta conta : contas) {
	        contasFormatadas.append("Numero: " + conta.getNumero()).append(" Status:" + (conta.status ? "ATIVA" : "INATIVA") + "\n");
	    }

	    return contasFormatadas.toString();
	}
	
    public float calcularBalançoTotal() {
        float saldoTotal = 0f;

        for (Conta conta : contas) {
            saldoTotal += conta.getSaldo();
        }

        return saldoTotal;
    }
	
	public String toStringPersonalizado() {
		return "Cliente: "+ nome +
				"\n" +
				"CPF: " + cpf + 
				"\n" +
				"Contas: " + (contas.isEmpty() ? "não possui" : contas);
	}
	
	public Conta localizarContaClientePorNumero(String num) {
	    for (Conta conta : contas) {
	        if (conta.getNumero().equals(num)) {
	            return conta;
	        }
	    }
	    return null;
	}
	
	public void salvarEmContasDoCliente() {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Cliente_" + this.getCpf()))) {
	        oos.writeObject(this);
	    } catch (IOException e) {
	        System.err.println("Erro ao salvar Cliente no arquivo: " + e.getMessage());
	    }
	}

	public void lerContasDoCliente() {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Cliente_" + this.getCpf()))) {
	        Cliente clienteLido = (Cliente) ois.readObject();
	        contas = clienteLido.contas;
	    } catch (ClassNotFoundException | IOException e) {
	        System.err.println("Erro ao ler Cliente do arquivo: " + e.getMessage());
	    }
	}
	    
	public void imprimirContasDoCliente() {
	    String nomeArquivo = "ContasDoCliente_" + this.cpf;  // 

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
	        Object obj = ois.readObject();

	        if (obj instanceof ArrayList<?>) {
	            ArrayList<Conta> contas = (ArrayList<Conta>) obj;

	            // Agora você pode iterar sobre a lista de contas e imprimir ou processar conforme necessário
	            for (Conta conta : contas) {
	                System.out.println(conta.toString());
	            }
	        } else {
	            System.err.println("Erro: O objeto lido não é uma ArrayList<Conta>.");
	        }
	    } catch (ClassNotFoundException | IOException e) {
	        System.err.println("Erro ao ler contas do arquivo: " + e.getMessage());
	    }
	}
	
	public static void main(String[] args) {	
		
		

	}//509136

}
