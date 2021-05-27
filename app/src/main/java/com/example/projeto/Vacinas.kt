package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Vacinas(var id: Long = -1, var nome: String, val doses: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaVacinas.CAMPO_NOME, nome)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Vacinas {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaVacinas.NOME_TABELA)
            val colDoses = cursor.getColumnIndex(TabelaVacinas.DOSES)


            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val doses = cursor.getInt(1)



            return Vacinas(id, nome, doses)
        }

    }
}