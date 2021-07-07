package com.example.projeto.classes

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.projeto.tabelas.TabelaPacientes
import java.util.*

data class Pacientes(
    var id: Long = -1,
    var nome: String,
    var nascimento: Date,
    var contacto: String,
    var nif: String,
    var morada: String
) {
    fun toContentValues(): ContentValues {

        val valores = ContentValues().apply {
            put(TabelaPacientes.CAMPO_NOME, nome)
            put(TabelaPacientes.DATA_NASCIMENTO, nascimento.time)
            put(TabelaPacientes.CAMPO_CONTACTO, contacto)
            put(TabelaPacientes.MORADA, morada)
           // put(TabelaPacientes.CAMPO_ID_MARCACOES, idmarcacoes)
            put(TabelaPacientes.NIF, nif)

        }
        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Pacientes {
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colNome = cursor.getColumnIndex(TabelaPacientes.CAMPO_NOME)
            val colMorada = cursor.getColumnIndex(TabelaPacientes.MORADA)
            val colNascimento = cursor.getColumnIndex(TabelaPacientes.DATA_NASCIMENTO)
            val colContacto = cursor.getColumnIndex(TabelaPacientes.CAMPO_CONTACTO)
            val colNif = cursor.getColumnIndex(TabelaPacientes.NIF)
           // val colIdMarcacoes = cursor.getColumnIndex(TabelaPacientes.CAMPO_ID_MARCACOES)

            val id = cursor.getLong(colId)
            val nome = cursor.getString(colNome)
            val nascimento = cursor.getLong(colNascimento)
            val contacto = cursor.getString(colContacto)
            val nif = cursor.getString(colNif)
            val morada = cursor.getString(colMorada)
            //val idmarcacoes = cursor.getLong(colIdMarcacoes)


            return Pacientes(id, nome, Date(nascimento), contacto, nif, morada)
        }

    }
}