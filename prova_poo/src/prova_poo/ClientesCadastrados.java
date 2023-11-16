package prova_poo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*Esta Classe salva em arquivo as instancias da Classe cliente
 * com seus atributos, sendo eles o cpf, o nome e o arraylist de contas 
 */

public class ClientesCadastrados {
	private static ArrayList<Cliente> clientes = new ArrayList<>();
	
	public static String getTotalClientes() {
		return String.valueOf(clientes.size());
	}

	public static void addCliente(Cliente cliente) 
	{
		if(!clientes.contains(cliente)) {
			clientes.add(cliente);
			salvarEmClientesCadastrados();
		}
	}
	
	public static void removeCliente(String cpf)
	{
		Cliente cliente = procurarClientePorCPF(cpf);
		if(clientes.contains(cliente)) {
			clientes.remove(cliente);
			salvarEmClientesCadastrados();
		}
	}
	
    public static Cliente procurarClientePorCPF(String cpf) {
    	lerClientesCadastrados();
    	
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente; // Retorna o cliente se encontrar
            }
        }
        return null; // Retorna null se não encontrar
    }
	
	public static ArrayList<Cliente> getClientesCadastrados() {
		return clientes;
	}
	
	
    public static void salvarEmClientesCadastrados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ClientesCadastrados"))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.err.println("Erro ao salvar clientes no arquivo: " + e.getMessage());
        }
    }
	
    @SuppressWarnings("unchecked")
	public static void lerClientesCadastrados() { 
    	/*Este método passa o conteudo do arquivo para um objeto e por fim
    	 * retorna para o ArrayList
    	 * */
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ClientesCadastrados"))) {
        	Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                clientes = (ArrayList<Cliente>) obj;
            }
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Erro ao ler clientes do arquivo: " + e.getMessage());
        }
    }
	
    public static String imprimirClientesCadastrados() {
    	
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ClientesCadastrados"))) {
            ArrayList<?> c = (ArrayList<?>) ois.readObject();
            return c.toString();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Erro ao ler clientes do arquivo: " + e.getMessage());
        }
		return null;
    }

    
	public static void main(String[] args) {
		System.out.println(imprimirClientesCadastrados());
	}



}
