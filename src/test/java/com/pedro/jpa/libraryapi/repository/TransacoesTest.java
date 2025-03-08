package com.pedro.jpa.libraryapi.repository;

import com.pedro.jpa.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /**
    * commmit -> confirmar alterações
    * rollback -> desfazer as alterações
    * */
    @Test
    void transacaoSimples(){
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
    }

}
