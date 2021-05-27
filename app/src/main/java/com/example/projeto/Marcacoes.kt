package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Marcacoes(var id: Long = -1, var nome: String, var data: Int, var idpaciente: Int, var idvacina: Int, var doses: Int ) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues()
        valores.put(TabelaMarcacoes.CAMPO_NOME, nome)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Marcacoes{
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colIdPacientes = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_PACIENTE)
            val colData = cursor.getColumnIndex(TabelaMarcacoes.DATA)
            val colDoses = cursor.getColumnIndex(TabelaMarcacoes.DOSES)
            val colIdVacinas = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)

            val id = cursor.getLong(0)
            val nome = cursor.getString(1)
            val idpaciente = cursor.getInt(1)
            val idvacina = cursor.getInt(1)
            val data = cursor.getInt(1)
            val doses = cursor.getInt(1)

            return Marcacoes(id, nome,idpaciente,idvacina, data, doses)
        }

    }
}