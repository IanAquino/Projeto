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
data class TestesBaseDados {
    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBdCovidOpenHelper() = BdCovid(getAppContext())

    private fun insereMarcacoes(tabela: TabelaMarcacoes, marcacoes: Marcacoes): Long {
        val db = getBdCovidOpenHelper().writableDatabase
        val id = tabela.insert(marcacoes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun inserePacientes(tabela: TabelaPacientes, pacientes: Pacientes): Long {
        val db = getBdCovidOpenHelper().writableDatabase
        val id = tabela.insert(pacientes.toContentValues())
        assertNotEquals(-1, id)

        return id
    }
    private fun insereVacina(tabela: TabelaVacinas, vacinas: Vacinas): Long {
        val db = getBdCovidOpenHelper().writableDatabase
        val id = tabela.insert(vacinas.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getMarcacoesBaseDados(tabela: TabelaMarcacoes, id: Long): Marcacoes {
        val cursor = tabela.query(
            TabelaMarcacoes.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Marcacoes.fromCursor(cursor)
    }

    private fun getVacinasBaseDados(tabela: TabelaVacinas, id: Long): Vacinas {
        val cursor = tabela.query(
            TabelaVacinas.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacinas.fromCursor(cursor)
    }
    private fun getPacientesBaseDados(tabela: TabelaPacientes, id: Long): Pacientes {
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
        val db = getBdCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirMarcacoes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaMarcacoes = TabelaMarcacoes(db)

        val marcacoes = Marcacoes(nome = "DIA D", )
        marcacoes.id = insereMarcacoes(tabelaMarcacoes, marcacoes)

        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))

        db.close()
    }

    @Test
    fun consegueAlterarMarcacoes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaMarcacoes = TabelaMarcacoes(db)

        val marcacoes = Marcacoes(nome = "DIA D")
        marcacoes.id = insereMarcacoes(tabelaMarcacoes, marcacoes)

        marcacoes.nome = "Dia 12"

        val registosAlterados = tabelaMarcacoes.update(
            marcacoes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))

        db.close()
    }

    @Test
    fun consegueEliminarMarcacoes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaMarcacoes = TabelaMarcacoes(db)

        val marcacoes = Marcacoes(nome = "Dia 12")
        marcacoes.id = insereMarcacoes(tabelaMarcacoes, marcacoes)

        val registosEliminados = tabelaMarcacoes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerMarcacoes() {
        val db = getBdCovidOpenHelper().writableDatabase
        val tabelaMarcacoes = TabelaMarcacoes(db)

        val marcacoes = Marcacoes(nome = "Dia 12")
        marcacoes.id = insereMarcacoes(tabelaMarcacoes, marcacoes)

        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))

        db.close()
    }

    @Test
    fun consegueInserirLivros() {
        val db = getBdCovidOpenHelper().writableDatabase

        val tabelaCategorias = TabelaCategorias(db)
        val categoria = Categoria(nome = "Aventura")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        val tabelaLivros = TabelaLivros(db)
        val livro = Livro(titulo = "O Leão que Temos Cá Dentro", autor = "Rachel Bright", idCategoria = categoria.id)
        livro.id = insereLivro(tabelaLivros, livro)

        assertEquals(livro, getLivroBaseDados(tabelaLivros, livro.id))

        db.close()
    }

    @Test
    fun consegueAlterarLivros() {
        val db = getBdLivrosOpenHelper().writableDatabase

        val tabelaCategorias = TabelaCategorias(db)

        val categoriaSuspense = Categoria(nome = "Suspense")
        categoriaSuspense.id = insereCategoria(tabelaCategorias, categoriaSuspense)

        val categoriaMisterio = Categoria(nome = "Mistério")
        categoriaMisterio.id = insereCategoria(tabelaCategorias, categoriaMisterio)

        val tabelaLivros = TabelaLivros(db)
        val livro = Livro(titulo = "?", autor = "?", idCategoria = categoriaSuspense.id)
        livro.id = insereLivro(tabelaLivros, livro)

        livro.titulo = "Ninfeias negras"
        livro.autor = "Michel Bussi"
        livro.idCategoria = categoriaMisterio.id

        val registosAlterados = tabelaLivros.update(
            livro.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(livro.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(livro, getLivroBaseDados(tabelaLivros, livro.id))

        db.close()
    }

    @Test
    fun consegueEliminarLivros() {
        val db = getBdLivrosOpenHelper().writableDatabase

        val tabelaCategorias = TabelaCategorias(db)
        val categoria = Categoria(nome = "Auto ajuda")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        val tabelaLivros = TabelaLivros(db)
        val livro = Livro(titulo = "?", autor = "?", idCategoria = categoria.id)
        livro.id = insereLivro(tabelaLivros, livro)

        val registosEliminados = tabelaLivros.delete(
            "${BaseColumns._ID}=?",
            arrayOf(livro.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerLivros() {
        val db = getBdLivrosOpenHelper().writableDatabase

        val tabelaCategorias = TabelaCategorias(db)
        val categoria = Categoria(nome = "Culinária")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        val tabelaLivros = TabelaLivros(db)
        val livro = Livro(titulo = "Chef profissional", autor = "Instituto Americano de Culinária", idCategoria = categoria.id)
        livro.id = insereLivro(tabelaLivros, livro)

        assertEquals(livro, getLivroBaseDados(tabelaLivros, livro.id))

        db.close()
    }
}