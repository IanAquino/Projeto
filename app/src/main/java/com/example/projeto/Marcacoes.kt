package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Marcacoes(var id: Long = -1, var data: Date, var idpaciente: Long, var idvacina: Long, var doses: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {

            put(TabelaMarcacoes.CAMPO_ID_PACIENTE, id)
            put(TabelaMarcacoes.DATA_VACINA, data.time)
            put(TabelaMarcacoes.DOSES, doses)
            put(TabelaMarcacoes.CAMPO_ID_VACINA, idvacina)
            put(TabelaMarcacoes.CAMPO_ID_PACIENTE, idpaciente)
        }
        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Marcacoes{
            val colId = cursor.getColumnIndex(BaseColumns._ID)
            val colIdPacientes = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_PACIENTE)
            val colData = cursor.getColumnIndex(TabelaMarcacoes.DATA_VACINA)
            val colDoses = cursor.getColumnIndex(TabelaMarcacoes.DOSES)
            val colIdVacinas = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)


            val id = cursor.getLong(colId)
            val datavacina = cursor.getLong(colData)
            val idPaciente = cursor.getLong(colIdPacientes)
            val idvacina = cursor.getLong(colIdVacinas)
            val doses = cursor.getInt(colDoses)

            return Marcacoes(id, idPaciente, idvacina, Date(datavacina), doses)
        }

    }
}