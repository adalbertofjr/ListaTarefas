package com.adalbertofjr.listatarefas.data;

/**
 * Created by AdalbertoF on 23/08/2015.
 */
public class TarefasContract {


    public static final class TarefaEntry{

        public static final String TABLE_NAME= "TAREFAS";
        public static final String ID = "_id";
        public static final String TITULO = "titulo";
        public static final String DATA_VENCIMENTO = "data_vencimento";
        public static final String PRIORIDADE = "prioridade";
        public static final String OBSERVACAO = "observacao";
        public static final String ENCERRADO = "encerrado";
    }
}
