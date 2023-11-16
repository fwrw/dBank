package prova_poo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class Tela extends JFrame{

	
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		private JTextArea consoleOutput;
	    private String menu1;


	    public Tela() {
	        // Configurar a janela
	        setTitle("Desgraçobank");
	        setFont(getFont());
	        setSize(700, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        aumentarFonteGlobal(6);


	        // Configurar JTextArea para exibir a "tela"
	        menu1 = 
	        
	        	"Bem-vindo ao Desgraçobank!" +
	        	"\n" +
	        	"[1] Cadastrar cliente.\n" +
	        	"[2] Remover cliente.\n" +
	        	"[3] Consultar cliente (CPF).\n" +
	        	"[4] Listar clientes.\n"+
       			"[5] Criar nova conta.\n" +
       			"[6] Remover conta do Cliente.\n" +
       			"[7] listar contas do Cliente.\n" +
       			"[8] Imprimir extrado da conta.\n" +
       			"[9] Consultar saldo da conta, \n" +
	       		"[10] Realizar depóstio.\n" +
	       		"[11] Realizar saque.\n" +
	  			"[12] Transferir saldo entre contas.\n"
	       			 
	       	
	       ;	        
	        
	        consoleOutput = new JTextArea(menu1);
	        consoleOutput.setEditable(false);
	        // Adicionar JTextArea a JScrollPane para permitir rolagem
	        JScrollPane scrollPane = new JScrollPane(consoleOutput);

	        // Adicionar JScrollPane à janela
	        add(scrollPane, BorderLayout.CENTER);


	        // Configurar um JTextField para receber comandos do usuário
	        JTextField userInput = new JTextField();
	        userInput.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                processarComando(userInput.getText());
	                userInput.setText("");
	            }
	        });

	        // Adicionar JTextField à parte inferior da janela
	        add(userInput, BorderLayout.SOUTH);
	    }
	    
	    private void aumentarFonteGlobal(int aumento) {
	        Font fontePadrao = UIManager.getFont("Label.font");
	        Font fonteMaior = new Font(fontePadrao.getName(), fontePadrao.getStyle(), fontePadrao.getSize() + aumento);
	        UIManager.put("Label.font", fonteMaior);
	        UIManager.put("Button.font", fonteMaior);
	        UIManager.put("TextField.font", fonteMaior);
	        UIManager.put("TextArea.font", fonteMaior); 
	        }

	    private void processarComando(String comando) {
	        // Simular processamento do comando e exibir na "tela"
	        try {
	            if (comando.equalsIgnoreCase("voltar")) {
	            	
	            		consoleOutput.setText(menu1);
	            	} else {
	                int opcao = Integer.parseInt(comando);
	                processarOpcao(opcao);
	            }
	        } catch (NumberFormatException e) {
	            consoleOutput.setText("Por favor, digite um número ou 'voltar'.\n");
	        }
	    }

	    private void processarOpcao(int opcao) {
	        switch (opcao) {
	            case 1:
	                
	                criarNovoCliente();
	                
	                
	                
	                break;
	            case 2:
	                
	                removerCliente();
	                break;
	            case 3:
	                
	                consultarCliente();
	                break;
	            case 4:
	                
	                listarClientes();
	                break;
	                
	            case 5:
	                criarConta();
	                break;
	            case 6:
	            	removerConta();
	            	break;
	            case 7:
	            	listarContas();
	            	break;
	            case 8:
	            	listarExtrato();
	            	break;
	            case 9:
	            	consultarSaldo();
	            	break;
	            case 10:
	            	realizarDeposito();
	            	break;
	            case 11:
	            	realizarSaque();
	            	break;
	            case 12:
	            	realizarTransferencia();
	            	break;

	            default:
	                consoleOutput.setText("Comando inválido. Por favor, escolha uma opção válida.\n");
	                break;
	        }
	    }
	    
	    @SuppressWarnings("unused")
		private void criarNovoCliente() {
	        // pega dados do usuario
	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do cliente:");
	        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");

	        // Cria um novo Objeto da classe Cliente apenas se cpf e nome NÃO estiverem vazios
	        if(cpf.isEmpty() && nome.isEmpty()) {
	        	consoleOutput.setText("Os campos não foram preenchidos corretamente. Digite voltar para retornar ao menu");
	        } else {	        	
	        	Cliente novoCliente = new Cliente(cpf, nome);
	        	consoleOutput.setText("Cliente criado com sucesso:\nNome: " + nome + "\nCPF: " + cpf);
	        }

	    }
	    
	    private void removerCliente() {
	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do Cliente:");
	    	
	    	Cliente removido = ClientesCadastrados.procurarClientePorCPF(cpf);
	    	ClientesCadastrados.removeCliente(cpf);
	    	
	    	consoleOutput.setText("Cliente removido com sucesso:\nNome: " + removido.getNome() + "\nCPF: " + removido.getCpf() );
	    }
	    
	    private void consultarCliente() {
	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do Cliente:");
	    	
	    	if(ClientesCadastrados.procurarClientePorCPF(cpf) != null) {
	    		consoleOutput.setText(ClientesCadastrados.procurarClientePorCPF(cpf).toStringPersonalizado());
	    	}
	    }
	    
	    private void listarClientes() {
	    	consoleOutput.setText(ClientesCadastrados.imprimirClientesCadastrados());
	    }
	    
	    private void criarConta() {
	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do proprietário: ");
	    	
	    	if(ClientesCadastrados.procurarClientePorCPF(cpf) != null) { // deve retornar um objeto Cliente
	    		Cliente proprietario = ClientesCadastrados.procurarClientePorCPF(cpf);
	    		
	    		Conta newConta = new Conta();
	    		proprietario.addConta(proprietario, newConta);
	    		newConta.proprietario = proprietario.getNome();
	    		
	    		
	    		consoleOutput.setText("Conta adcionada com sucesso! \n"
	    				+ "Número da conta: " + newConta.getNumero());
	    	} else if (ClientesCadastrados.procurarClientePorCPF(cpf) == null) {
	    		consoleOutput.setText("Nem tem esse caba aqui mermao tas viajano eh ?? \n \n Digite 'voltar' para retornar ao Menu.");
	    	}
	    }
	    
	    private void removerConta() {
	        String cpf = JOptionPane.showInputDialog("Digite o CPF do proprietário da Conta: ");
	        Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);
	        if (cliente != null) {
	            cliente.lerContasDoCliente();
	            if (cliente.possuiContas()) {
	                String num = JOptionPane.showInputDialog("Digite o número da Conta para remover: ");
	                cliente.deleteConta(num);
	                cliente.salvarEmContasDoCliente();
	                consoleOutput.setText("Conta removida com sucesso");
	            } else {
	                JOptionPane.showMessageDialog(null,
	                        "O cliente não possui contas cadastradas.\nDigite voltar para retornar ao Menu.",
	                        "Sem Contas",
	                        JOptionPane.WARNING_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(null,
	                    "erro neh",
	                    "Cliente não Encontrado",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    private void listarContas() {
	    	
	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do proprietário das Contas: ");
	    	
	    	if(ClientesCadastrados.procurarClientePorCPF(cpf) != null) {
	    		Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);
	    		cliente.lerContasDoCliente();
	    		consoleOutput.setText(cliente.getContas());
	    	}else {
	    		consoleOutput.setText("Não foi possivel encontrar um cliente com esse cpf. Digite voltar para retornar ao Menu.");
	    	}
	    }
	    
	    private void listarExtrato() {
	    	
	    	String num = JOptionPane.showInputDialog("Digite o número da Conta que deseja ver extrato: ");
	    	
	    	if(Conta.localizarContaPorNumero(num) != null) {
	    		Conta conta = Conta.localizarContaPorNumero(num);
	    		consoleOutput.setText("EXTRATO DA CONTA: " + num + "\n" + conta.imprimirExtrato());
	    	} else {
	    		//retornou null
	    	}
	    	
	    }
	    
	    private void consultarSaldo() {
	        String num = JOptionPane.showInputDialog("Digite o número da Conta que deseja consultar o Saldo: ");

	        // Antes de tentar localizar a conta, certifique-se de ler as contas cadastradas
	        Conta.lerContasCadastradas();

	        if (Conta.localizarContaPorNumero(num) != null) {
	        	Conta conta = Conta.localizarContaPorNumero(num);
	            consoleOutput.setText("Conta: " + num +
	            		"\n" +
	            		"\nSALDO: " + conta.getSaldo());
	            System.out.println(conta.getSaldo());
	        } else {
	            JOptionPane.showMessageDialog(null,
	                    "Essa conta aí é de outro banco",
	                    "Conta não Encontrada",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    @SuppressWarnings("unused")
	    private void realizarDeposito() {
	        String cpf = JOptionPane.showInputDialog("Digite o cpf do Proprietario da conta: ");
	        Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);

	        if (cliente != null) {
	            cliente.lerContasDoCliente();
	            Conta.lerContasCadastradas();

	            String num = JOptionPane.showInputDialog("Digite o número da Conta que deseja depositar: ");
	            Conta conta = Conta.localizarContaPorNumero(num);

	            if (conta != null && conta.status) {
	                try {
	                    String saldoStr = JOptionPane.showInputDialog("Digite a quantia que deseja Depositar");
	                    float saldo = Float.parseFloat(saldoStr);
	                    conta.depositar(saldo);
	                    consoleOutput.setText("DEPÓSITO DE " + saldoStr + " DINHEIROS REALIZADO NA CONTA: " + num + ".");
	                    cliente.salvarEmContasDoCliente();
	                    Conta.salvarEmContasCadastradas();
	                } catch (NumberFormatException e) {
	                    JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
	                }
	            } else if (conta != null && !conta.status) {
	                JOptionPane.showMessageDialog(null, "Essa conta ai foi de comes e bebes.", "Conta Inativa", JOptionPane.ERROR_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "Essa conta ai eh do nubank, tá viajando é?", "Conta não encontrada", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Essa caba não é daqui não.", "Cliente não existente", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    private void realizarSaque() {
	        String cpf = JOptionPane.showInputDialog("Digite o cpf do Proprietario da conta: ");
	        Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);

	        if (cliente != null) {
	            cliente.lerContasDoCliente();
	            Conta.lerContasCadastradas();

	            String num = JOptionPane.showInputDialog("Digite o número da Conta que deseja sacar: ");
	            Conta conta = Conta.localizarContaPorNumero(num);

	            if (conta != null && conta.status) {
	                try {
	                    String saqueStr = JOptionPane.showInputDialog("Digite a quantia que deseja Sacar");
	                    float saque = Float.parseFloat(saqueStr);

	                    if (saque > 0 && saque <= conta.getSaldo()) {
	                        conta.sacar(saque);
	                        consoleOutput.setText("SAQUE DE " + saqueStr + " DINHEIROS REALIZADO NA CONTA: " + num + ".");
	                        cliente.salvarEmContasDoCliente();
	                        Conta.salvarEmContasCadastradas();
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Valor de saque inválido ou saldo insuficiente.", "Operação Inválida", JOptionPane.ERROR_MESSAGE);
	                    }
	                } catch (NumberFormatException e) {
	                    JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
	                }
	            } else if (conta != null && !conta.status) {
	                JOptionPane.showMessageDialog(null, "Essa conta ai foi de comes e bebes.", "Conta Inativa", JOptionPane.ERROR_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(null, "Essa conta ai eh do nubank, tá viajando é?", "Conta não encontrada", JOptionPane.ERROR_MESSAGE);
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Essa caba não é daqui não.", "Cliente não existente", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    private void realizarTransferencia() {
	    	Conta.lerContasCadastradas();
	        String cpf = JOptionPane.showInputDialog("Digite o CPF do Proprietario da conta: ");
	        
	        Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);
	        cliente.lerContasDoCliente();
	        Conta.lerContasCadastradas();

	        String num = JOptionPane.showInputDialog("Digite o numero da conta de Origem: ");
			Conta contaOrigem = Conta.localizarContaPorNumero(num);
			
			
			
			if (contaOrigem != null && contaOrigem.status) {
				
			    String quantiaStr = JOptionPane.showInputDialog("Digite a Quantia que deseja transferir");
			    System.out.println(quantiaStr);
			    System.out.println(Float.parseFloat(quantiaStr)); 
			    ClientesCadastrados.lerClientesCadastrados();
			    System.out.println(contaOrigem.getSaldo());
			    try {
			        float quantia = Float.parseFloat(quantiaStr);

			        if (quantia > 0) {
			            cliente.lerContasDoCliente();
			            if (contaOrigem.getSaldo() >= quantia) {
			                String numDestino = JOptionPane.showInputDialog("Digite o numero da conta de Destino: ");
			                Conta contaDestino = Conta.localizarContaPorNumero(numDestino);

			                if (contaDestino != null) {
			                    contaOrigem.transferir(quantia, contaDestino);
			                    cliente.salvarEmContasDoCliente();
			                    Conta.salvarEmContasCadastradas();

			                    JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso",
			                            "Operação concluída", JOptionPane.INFORMATION_MESSAGE);
			                } else {
			                    JOptionPane.showMessageDialog(null, "Conta destino não encontrada",
			                            "Conta não encontrada", JOptionPane.ERROR_MESSAGE);
			                }
			            } else {
			                JOptionPane.showMessageDialog(null, "Saldo insuficiente para realizar a transferência",
			                        "Operação inválida", JOptionPane.ERROR_MESSAGE);
			            }
			        } else {
			            JOptionPane.showMessageDialog(null, "Quantia inválida. Insira um valor maior que zero",
			                    "Operação inválida", JOptionPane.ERROR_MESSAGE);
			        }
			    } catch (NumberFormatException e) {
			        JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.",
			                "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
			    }
			} else {
			    JOptionPane.showMessageDialog(null, "Conta de origem não encontrada ou inativa",
			            "Operação inválida", JOptionPane.ERROR_MESSAGE);
			}
	    }
//	    private void balancoTotal() {
//	    	ClientesCadastrados.lerClientesCadastrados();
//	    	String cpf = JOptionPane.showInputDialog("Digite o CPF do Cliente: ");
//	    	if(ClientesCadastrados.procurarClientePorCPF(cpf) != null) {
//	    		Cliente cliente = ClientesCadastrados.procurarClientePorCPF(cpf);
//	    		cliente.lerContasDoCliente();
//	    		System.out.println(cliente.calcularBalançoTotal());
//	    	}
	    	
//	    }

	    	
	   }

	
	
	



