package br.edu.up.as.dao;

import java.util.List;
import br.edu.up.as.entidade.Produto;
import javax.persistence.EntityManager;

public class ProdutoDao implements Dao<Produto> {

	public void salvar(Produto o) {		
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
	}

	public void excluir(Produto o) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.getReference(Produto.class, o.getId()));
		em.getTransaction().commit();
	}

	public void alterar(Produto o) {
		EntityManager em = Conexao.getEntityManager();
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listar() {
		EntityManager em = Conexao.getEntityManager();
		return (List<Produto>)em.createQuery("SELECT p FROM Produto p").getResultList();
	}

	public Produto buscar(Integer id) {
		EntityManager em = Conexao.getEntityManager();
		return em.find(Produto.class, id);
	}
}
