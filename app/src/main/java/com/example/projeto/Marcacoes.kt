package com.example.projeto

import android.content.ContentValues

data class Marcacoes(var id: Long = -1, var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaMarcacoes.CAMPO_NOME, nome)

        return valores
    }
    companion object{
        fun fromCursor(): Marcacoes{
            val cursor
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_NOME_PACIENTE)
            val colNascimento = cursor.getColumnIndex(TabelaMarcacoes.DATA_MARCADA)
            val colContacto = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_IDADE)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val data = cursor.getInteger(1)
            val idade = cursor.getInteger(1)

            return Marcacoes(id, nome, data, idade)
        }

    }
}