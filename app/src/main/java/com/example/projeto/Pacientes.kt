package com.example.projeto

import android.content.ContentValues

data class Pacientes(var id: Long = -1, var nome: String) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaPacientes.CAMPO_NOME, nome)

        return valores 
    }
    companion object{
        fun fromCursor(): Pacientes{
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaCategorias.CAMPO_NOME)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)

            return Categoria(id, nome)
        }

    }
}