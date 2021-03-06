package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.modelo.TipoMovimentacao;
import br.com.caelum.financas.util.JPAutil;

public class TestaMovimentacao {
	public static void main(String[] args) {
		EntityManager manager = new JPAutil().getEntityManager();
		Movimentacao mov = new Movimentacao();
		mov.setValor(new BigDecimal("0.1"));
		mov.setData(Calendar.getInstance());
		mov.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
		mov.setDescricao("Gastando muito!");
		manager.getTransaction().begin();
		// problema ocorre tambem se eu preencher os dados da conta e da 
		// movimentacao ao mesmo tempo @ManyToOne(cascade=CascaeType.PERSIST)
		Conta conta = manager.find(Conta.class,4); //op1
		//op2 manager.persist(new Conta(construtor tem que estar bem feito));//ou -bem pior
		mov.setConta(conta);
		Movimentacao mov2 = manager.find(Movimentacao.class,1);
		mov2.setValor(new BigDecimal("8"));
		manager.persist(mov2);
		manager.getTransaction().commit();
		manager.close();
	}
}
