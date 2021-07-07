package com.example.projeto

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.projeto.tabelas.TabelaMarcacoes
import com.example.projeto.tabelas.TabelaPacientes
import com.example.projeto.tabelas.TabelaVacinas

class BDCovidOpenHelper(context: Context?)
    : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS) {
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            TabelaPacientes(db).cria()
            TabelaMarcacoes(db).cria()
            TabelaVacinas(db).cria()
        }
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val NOME_BASE_DADOS = "projeto.db"
        const val VERSAO_BASE_DADOS = 1
    }
}