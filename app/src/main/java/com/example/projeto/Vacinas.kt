package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacinas(var id: Long = -1, var nome: String, val entrada: Any, val saida: Any, val categoria: Any) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVacinas.CAMPO_NOME, nome)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Vacinas {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaVacinas.NOME_TABELA)
            val colEntrada = cursor.getColumnIndex(TabelaVacinas.ENTRADA_VACINAS)
            val colSaida = cursor.getColumnIndex(TabelaVacinas.SAIDA_VACINAS)
            val colCategoria = cursor.getColumnIndex(TabelaVacinas.CATEGORIA)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val entrada = cursor.getInt(1)
            val saida = cursor.getInt(1)
            val categoria = cursor.getString(1)


            return Vacinas(id, nome, entrada, saida, categoria)
        }

    }
}