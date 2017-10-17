package br.edu.up.as.teste;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import br.edu.up.as.entidade.Cliente;
import br.edu.up.as.service.clienteService;
import br.edu.up.as.service.ServiceException;


public class TestarCliente {
	
	// vars para executar testes
	public static clienteService service= new clienteService();
	public static Cliente testObject = new Cliente();
	
    @BeforeClass
    public static void before() throws ServiceException {
    	testObject.setNome("Cliente para execu��o de testes");
    	service.salvar(testObject);
    }

    @AfterClass
    public static void after() {
    	service.excluir(testObject);
    }
    
	@Test
	public void cadastrarSuccess() throws ServiceException {
		Cliente o = new Cliente();
		o.setNome("Teste");
		
		// salva o objeto
		service.salvar(o);
		
		// verifica os valores
		assertEquals(true, o.getId() != null);
		
		// verifica se os dados cadastrados sao iguais
		Cliente persistedObject = service.buscar(o.getId());
		assertEquals(true, o.getId() == persistedObject.getId());
		
		// exclui o objeto para nao poluir o banco
		service.excluir(o);
	}
	
	@Test(expected = ServiceException.class)
	public void cadastrarError() throws ServiceException {
		Cliente o = new Cliente();
		
		o.setNome(null);
		
		// verifica se o cliente foi cadastrado com erro
		assertEquals(false, o.getId() != null);
		assertEquals(false, o.getNome() != null);
		
		// verifica se n�o salva o objeto
		service.salvar(o);
		assertEquals(false, service.buscar(o.getId()) != null);
	}
	
	@Test
	public void buscarSuccess() {
		Cliente o = service.buscar(service.listar().get(0).getId());
	
		// verifica se um objeto foi encontrado
		assertEquals(true, o != null);
		assertEquals(true, o.getId() != null);
	}
	
	@Test(expected = NullPointerException.class)
	public void buscarError() {
		Cliente o = service.buscar(0);
		
		// verifica se nenhum objeto foi encontrado
		assertEquals(false, o != null);
		assertEquals(false, o.getId() != null);
	}
	
	@Test
	public void alterarSuccess() throws ServiceException {
		Cliente o = service.buscar(service.listar().get(0).getId());
	
		// altera o objeto
		o.setNome("Teste Alterado");
		service.alterar(o);
		
		// verifica se o objeto foi alterado
		assertEquals(true, o.getId() != null);
		assertEquals(true, service.buscar(o.getId()).getNome().equals("Teste Alterado"));
	}
	
	@Test(expected = ServiceException.class)
	public void alterarError() throws ServiceException {
		Cliente o = service.buscar(service.listar().get(0).getId());
	
		// altera o objeto
		o.setNome(null);
		
		// verifica se o objeto foi alterado
		assertEquals(true, o.getId() != null);
		assertEquals(true, service.buscar(o.getId()).getNome() != null);

		service.alterar(o);
	}
	
	@Test
	public void listarSuccess() {		
		// verifica se o tamanho da lista encontrada � maior que zero
		assertEquals(true, service.listar().size() > 0);
	}
	
	@Test
	public void excluirSuccess() throws ServiceException {
		Cliente o = null;
		
		// salva um objeto para ser exluido
		o = new Cliente();
		o.setNome("Teste Excluir");
		service.salvar(o);
		
		// verifica se o objeto foi salvo
		assertEquals(true, service.buscar(o.getId()) != null);
		
		// exclui o objeto
		service.excluir(o);
		
		// verifica se o objeto foi exluido com sucesso
		assertEquals(true, service.buscar(o.getId()) == null);
	}
}
