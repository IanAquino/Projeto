package com.example.projeto

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.util.*

data class Marcacoes(var id: Long = -1, var idvacina: Long, var idpaciente: Long, var datavacina: Date, var doses: Int) {
    fun toContentValues(): ContentValues {
        val valores = ContentValues().apply {

            put(TabelaMarcacoes.CAMPO_ID_PACIENTE, id)
            put(TabelaMarcacoes.DATA_VACINA, datavacina.time)
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
            val colDataVacina = cursor.getColumnIndex(TabelaMarcacoes.DATA_VACINA)
            val colDoses = cursor.getColumnIndex(TabelaMarcacoes.DOSES)
            val colIdVacinas = cursor.getColumnIndex(TabelaMarcacoes.CAMPO_ID_VACINA)


            val id = cursor.getLong(colId)
            val datavacina = cursor.getLong(colDataVacina)
            val idvacina = cursor.getLong(colIdVacinas)
            val idPaciente = cursor.getLong(colIdPacientes)
            val doses = cursor.getInt(colDoses)

            return Marcacoes(id, idPaciente, idvacina, Date(datavacina), doses)
        }

    }
}