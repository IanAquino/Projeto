package com.example.projeto



import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestesBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdTeste() = BdTeste(getAppContext())

    private fun inserePaciente(tabela: TabelaPacientes, categoria: Pacientes): Long {
        val id = tabela.insert(categoria.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdTeste.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdTeste().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirPacientes() {
        val db = getBdTeste().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val nome = Pacientes (nome = "ALfa")
        nome.id = inserePaciente(tabelaPacientes, nome)

        db.close()
    }

    @Test
    fun consegueAlterarPacientes() {
        val db = getBdTeste().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val nome = Pacientes (nome = "Beta")
        nome.id = inserePaciente(tabelaPacientes, nome)

        nome.nome = "Pedro"

        val registosAlterados = tabelaPacientes.update(
            nome.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(nome.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test
    fun consegueEliminarPacientes() {
        val db = getBdTeste().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val nome = Pacientes (nome = "Alfa")
        nome.id = inserePaciente(tabelaPacientes, nome)

        val cursor = tabelaPacientes.query(
            TabelaPacientes.,
            "${BaseColumns._ID}=?",
            arrayOf(nome.id.toString())
            null, null,null

        )
        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        val registosEliminados = tabelaPacientes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(nome.id.toString())
        )




        assertEquals(1, registosEliminados)

        db.close()
    }
}