package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Pacientes (var id: Long = -1, var nome: String, var nascimento: Int, var contacto: Int, var nif: Int) {
    fun toContentValues(): ContentValues {

        val valores = ContentValues()
        valores.put(TabelaPacientes.CAMPO_NOME, nome)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Pacientes{
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPacientes.CAMPO_NOME)
            val colNascimento = cursor.getColumnIndex(TabelaPacientes.DATA_NASCIMENTO)
            val colContacto = cursor.getColumnIndex(TabelaPacientes.CAMPO_CONTACTO)
            val colNif = cursor.getColumnIndex(TabelaPacientes.NIF)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val nascimento = cursor.getInt(1)
            val contacto = cursor.getInt(1)
            val nif = cursor.getInt(1)

            return Pacientes(id, nome, nascimento, contacto, nif)
        }

    }
}