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
            val colNome = cursor.getColumnIndex(TabelaPacientes.CAMPO_NOME)
            val colNascimento = cursor.getColumnIndex(TabelaPacientes.DATA_NASCIMENTO)
            val colContacto = cursor.getColumnIndex(TabelaPacientes.CAMPO_CONTACTO)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val nascimento = cursor.getInteger(1)
            val contacto = cursor.getInteger(1)

            return Pacientes(id, nome, nascimento, contacto)
        }

    }
}