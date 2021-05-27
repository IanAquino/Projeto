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
    private fun getBdCovid() = BdCovid(getAppContext())

    private fun inserePaciente(tabela: TabelaPacientes, id: Pacientes, nascimento: Pacientes, contacto: Pacientes): Long {
        val id = tabela.insert(id.toContentValues())
        val nascimento = tabela.insert(nascimento.toContentValues())
        val contacto = tabela.insert(contacto.toContentValues())

        assertNotEquals(-1, id)

        return id
    }

    private fun getPacienteBaseDados(tabela: TabelaPacientes, id: Long): Pacientes {
        val cursor = tabela.query(
            TabelaPacientes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pacientes.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdCovid.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdCovid().readableDatabase
        assert(db.isOpen)
        db.close()
    }




    fun consegueInserirPacientes() {
        val db = getBdCovid().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val nome = Pacientes (nome = "ALfa")
        val nascimento = Pacientes (nascimento = 123 )
        val contacto = Pacientes (contacto = 964964)
        nome.id = inserePaciente(tabelaPacientes, nome, nascimento, contacto)
        assertEquals(nome, getPacienteBaseDados(tabelaPacientes, nome.id))

        db.close()
    }

    @Test
    fun consegueAlterarPacientes() {
        val db = getBdCovid().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes (nome = "Beta")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        pacientes.nome = "Pedro"

        val registosAlterados = tabelaPacientes.update(
            pacientes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(pacientes, getPacienteBaseDados(tabelaPacientes, pacientes.id))

        db.close()
    }

    @Test
    fun consegueEliminarPacientes() {
        val db = getBdCovid().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes (nome = "Alfa")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        /*val cursor = tabelaPacientes.query(
            TabelaPacientes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
            null, null,null

        )*/
        //assertNotNull(cursor)
        //assert(cursor!!.moveToNext())

        val registosEliminados = tabelaPacientes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }
    @Test
    fun consegueLerPacientes() {
        val db = getBdCovid().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(nome = "Aventura")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        assertEquals(pacientes, getPacienteBaseDados(tabelaPacientes, pacientes.id))

        db.close()
    }
}