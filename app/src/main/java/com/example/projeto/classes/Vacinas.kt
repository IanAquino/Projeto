package com.example.projeto.classes

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.projeto.tabelas.TabelaVacinas

data class Vacinas(var id: Long = -1, var nome: String, val doses: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {

            put(TabelaVacinas.DOSES, doses)
            put(TabelaVacinas.CAMPO_NOME, nome)
        }
        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Vacinas {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaVacinas.NOME_TABELA)
            val colDoses = cursor.getColumnIndex(TabelaVacinas.DOSES)


            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val doses = cursor.getInt(colDoses)



            return Vacinas(id, nome, doses)
        }

    }
}