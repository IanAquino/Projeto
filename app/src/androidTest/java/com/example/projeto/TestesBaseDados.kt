package com.example.projeto


import android.provider.BaseColumns
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.projeto.classes.Pacientes
import com.example.projeto.tabelas.TabelaPacientes
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TesteBaseDados {


    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())

    private fun inserePaciente(tabela: TabelaPacientes, pacientes: Pacientes): Long {
        val id = tabela.insert(pacientes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getPacienteBaseDados(tabela: TabelaPacientes, id: Long): Pacientes {
        val cursor = tabela.query(
            TabelaPacientes.TODAS_COLUNAS,
            "${TabelaPacientes.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Pacientes.fromCursor(cursor)
    }

    /* private fun getMarcacoesBaseDados(tabela: TabelaMarcacoes, id: Long): Marcacoes {
        val cursor = tabela.query(
            TabelaMarcacoes.TODAS_COLUNAS,
            "${TabelaMarcacoes.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Marcacoes.fromCursor(cursor)
    }

    private fun insereMarcacao(tabela: TabelaMarcacoes, marcacoes: Marcacoes): Long {
        val id = tabela.insert(marcacoes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getVacinasBaseDados(tabela: TabelaVacinas, id: Long): Vacinas {
        val cursor = tabela.query(
            TabelaVacinas.TODAS_COLUNAS,
            "${TabelaVacinas.NOME_TABELA}.${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacinas.fromCursor(cursor)
    }*/


    /*private fun insereVacinas(tabela: TabelaVacinas, vacinas: Vacinas): Long {
        val id = tabela.insert(vacinas.toContentValues())
        assertNotEquals(1, id)

        return id
    }*/


    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BDCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }


    //Tabela Paciente

    @Test
    fun consegueInserirPaciente() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(
            nome = "Joao",
            nascimento = Date(2020 / 12 / 12),
            contacto = "123456",
            nif = "123456",
            morada = "rua 21"
        )
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        db.close()

    }

    @Test
    fun consegueAlterarPaciente() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(
            nome = "Joao",
            nascimento = Date(2020 / 12 / 12),
            contacto = "123456",
            nif = "123456",
            morada = "rua 21"
        )
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)
        pacientes.nome = "Pedro"

        val registoAlterados = tabelaPacientes.update(
            pacientes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(1, registoAlterados)
        assertEquals(pacientes, getPacienteBaseDados(tabelaPacientes, pacientes.id))
        db.close()
    }

    @Test
    fun consegueEliminarPaciente() {
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(
            nome = "Pedro",
            nascimento = Date(2020 / 12 / 12),
            contacto = "123456",
            nif = "123456",
            morada = "rua 21"
        )
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        val registosEliminados = tabelaPacientes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(1, registosEliminados)
        db.close()
    }

    //@Test
    fun consegueLerPaciente() {
        val db = getBDCovidOpenHelper().writableDatabase


        val tabelaPacientes = TabelaPacientes(db)
        val pacientes = Pacientes(
            nome = "Pedro",
            nascimento = Date(2020 / 12 / 12),
            contacto = "123456",
            nif = "123456",
            morada = "rua 21"
        )
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        assertEquals(pacientes, getPacienteBaseDados(tabelaPacientes, pacientes.id))

        db.close()
    }


}








