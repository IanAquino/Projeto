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
    private fun getBdLivrosOpenHelper() = BdCovid(getAppContext())

    private fun insereCategoria(tabela: TabelaMarcacoes, categoria: Marcacao): Long {
        val id = tabela.insert(categoria.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun insereLivro(tabela: TabelaLivros, livro: Livro): Long {
        val id = tabela.insert(livro.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun getCategoriaBaseDados(tabela: TabelaCategorias, id: Long): Categoria {
        val cursor = tabela.query(
            TabelaCategorias.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Categoria.fromCursor(cursor)
    }

    private fun getLivroBaseDados(tabela: TabelaLivros, id: Long): Livro {
        val cursor = tabela.query(
            TabelaLivros.TODAS_COLUNAS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null, null, null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Livro.fromCursor(cursor)
    }

    @Before
    fun apagaBaseDados() {
        getAppContext().deleteDatabase(BdLivrosOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val db = getBdLivrosOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }

    @Test
    fun consegueInserirCategorias() {
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = TabelaCategorias(db)

        val categoria = Categoria(nome = "Drama")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        assertEquals(categoria, getCategoriaBaseDados(tabelaCategorias, categoria.id))

        db.close()
    }

    @Test
    fun consegueAlterarCategorias() {
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = TabelaCategorias(db)

        val categoria = Categoria(nome = "sci")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        categoria.nome = "Ficção científica"

        val registosAlterados = tabelaCategorias.update(
            categoria.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(categoria, getCategoriaBaseDados(tabelaCategorias, categoria.id))

        db.close()
    }

    @Test
    fun consegueEliminarCategorias() {
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = TabelaCategorias(db)

        val categoria = Categoria(nome = "teste")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        val registosEliminados = tabelaCategorias.delete(
            "${BaseColumns._ID}=?",
            arrayOf(categoria.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerCategorias() {
        val db = getBdLivrosOpenHelper().writableDatabase
        val tabelaCategorias = TabelaCategorias(db)

        val categoria = Categoria(nome = "Aventura")
        categoria.id = insereCategoria(tabelaCategorias, categoria)

        assertEquals(categoria, getCategoriaBaseDados(tabelaCategorias, categoria.id))

        db.close()
    }

    @Test
    fun consegueInserirLivros() {
        val db = getBdLivrosOpenHelper().writableDatabase

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