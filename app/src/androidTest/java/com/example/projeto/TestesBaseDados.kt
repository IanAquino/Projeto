package com.example.projeto

import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projeto.classes.*
import com.example.projeto.tabelas.*


import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
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

    private  fun inserePaciente(tabela: TabelaPacientes, pacientes: Pacientes): Long {
        val id = tabela.insert(pacientes.toContentValues())
        assertNotEquals(pacientes, id)

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
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BDCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados(){

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }


    //Tabela Paciente

    @Test
    fun consegueInserirPaciente(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(nome = "Joao", nascimento = Date(2020/12/12), contacto = "123456", nif = "123456", morada = "rua 21")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        db.close()

    }

    @Test
    fun consegueAlterarPaciente(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(nome = "Joao", nascimento = Date(2020/12/12), contacto = "123456", nif = "123456", morada = "rua 21")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)
        pacientes.nome = "Pedro"

        val registoAlterados = tabelaPacientes.update(
            pacientes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(0, registoAlterados)
        db.close()
    }

    @Test
    fun consegueEliminarPaciente(){
        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaPacientes = TabelaPacientes(db)

        val pacientes = Pacientes(nome = "Pedro", nascimento = Date(2020/12/12), contacto = "123456", nif = "123456", morada = "rua 21")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        val registosEliminados= tabelaPacientes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(pacientes.id.toString())
        )

        assertEquals(0, registosEliminados)
        db.close()
    }

    //@Test
    fun consegueLerPaciente() {
        val db = getBDCovidOpenHelper().writableDatabase


        val tabelaPacientes = TabelaPacientes(db)
        val pacientes = Pacientes(nome = "Pedro", nascimento = Date(2020/12/12), contacto = "123456", nif = "123456", morada = "rua 21")
        pacientes.id = inserePaciente(tabelaPacientes, pacientes)

        assertEquals(1, getPacienteBaseDados(tabelaPacientes, pacientes.id))

        db.close()
    }




    //Tabela Marcacoes

    /*@Test
    fun consegueInserirMarcacoes(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(datavacina = Date(2020/12/12), doses = 1, idvacina = vacina.id, idpaciente = pacientes.id )
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(datavacina = Date(2020/12/12), doses = 1, idvacina = vacina.id, idpaciente = pacientes.id )
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))
        db.close()

    }


    @Test
    fun consegueAlterarMarcacoes() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaMarcacoes = TabelaMarcacoes(db)

        val marcacoesAtual = Marcacoes(datavacina = Date(2020/12/12), doses = 1, idvacina = vacina.id, idpaciente = pacientes.id )
        marcacoesAtual.id = insereMarcacao(tabelaMarcacoes, marcacoesAtual)

        val marcacoesNovo = Marcacoes(datavacina = Date(2021/12/12), doses = 2, idvacina = vacina.id, idpaciente = pacientes.id)
        marcacoesNovo.id = insereMarcacao(tabelaMarcacoes, marcacoesNovo)

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(doses = "?", datavacina= Date(2020,10,20) ,idmarcacoes = marcacoesAtual.id)
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        marcacoes.datavacina = Date(2020,10,20)
        marcacoes.doses = 1
        marcacoes.idvacina= vacina.id
        marcacoes.idpaciente = pacientes.id

        val registosAlterados = tabelaMarcacoes.update(
           marcacoes.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(idpaciente, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))

        db.close()
    }


    @Test
    fun consegueEliminarMarcacoes() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(datavacina = Date(2021/12/12), doses = 2, idvacina = vacina.id, idpaciente = pacientes.id)
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(doses = "?", datavacina= Date(2020,10,20) ,idmarcacoes = marcacoesAtual.id)
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        val registosEliminados = tabelaMarcacoes.delete(
            "${BaseColumns._ID}=?",
            arrayOf(marcacoes.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }


    @Test
    fun consegueLerMarcacoes() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes(datavacina = Date(2020/12/12), doses = 1, idvacina = vacina.id, idpaciente = pacientes.id)
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        val tabelaMarcacoes = TabelaMarcacoes(db)
        val marcacoes = Marcacoes( datavacina = Date(2020/12/12), doses = 1, idvacina = vacina.id, idpaciente = pacientes.id)
        marcacoes.id = insereMarcacao(tabelaMarcacoes, marcacoes)

        assertEquals(marcacoes, getMarcacoesBaseDados(tabelaMarcacoes, marcacoes.id))

        db.close()
    }




    //Tabela Vacinas


    @Test
    fun consegueInserirVacinas(){
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas(nome = "Pfizer", doses = 2)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas(nome = "Astrazeneca", doses = 2)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas (nome = "Jassen", doses = 1)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        assertEquals(vacinas, getVacinasBaseDados(tabelaVacinas, vacinas.id))
        db.close()

    }

    @Test
    fun consegueAlterarVacinas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)

        val vacinaAtual = Vacinas(nome = "Pfizer", doses = 2)
        vacinaAtual.id = insereVacinas(tabelaVacinas, vacinaAtual)

        val vacinaNova = Vacinas(nome = "CoronaVac", doses = 2)
        vacinaNova.id = insereVacinas(tabelaVacinas, vacinaNova)

        /*val tabelaVacinas = TabelaVacinas(db)
        val vacina = Vacinas (nome = "?", sexo = "?", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito = distritoAtual.id, nomeDistrito = distritoAtual.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)*/

        vacinaNova.nome = "vacina1"
        vacinaNova.doses = 1



        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas(nome = "CoronaVac2", doses = 2)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        vacinas.doses = 2
        vacinas.nome = "vacinagripe"



        val registosAlterados = tabelaVacinas.update(
            vacinas.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacinas.id.toString())
        )

        assertEquals(1, registosAlterados)

        assertEquals(vacinas, getVacinasBaseDados(tabelaVacinas, vacinas.id))

        db.close()
    }

    @Test
    fun consegueEliminarVacinas() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas(nome = "Pfizer", doses = 2)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

       /* val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa(nome = "?", sexo = "?", data_nascimento= Date(2020,10,20),telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTeste = TabelaTestes(db)
        val teste = Teste(temperatura=0.0f, sintomas = "?", estado_saude = "?",data_teste= Date(2020,10,20), id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTeste, teste)*/

        val registosEliminados = tabelaVacinas.delete(
            "${BaseColumns._ID}=?",
            arrayOf(vacinas.id.toString())
        )

        assertEquals(1, registosEliminados)

        db.close()
    }

    @Test
    fun consegueLerTeste() {
        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaVacinas = TabelaVacinas(db)
        val vacinas = Vacinas(nome = "Astrazeneca", doses = 2)
        vacinas.id = insereVacinas(tabelaVacinas, vacinas)

        /*val tabelaPessoas = TabelaPessoas(db)
        val pessoa = Pessoa( nome = "Jose", sexo = "Masculino",data_nascimento= Date(2020,10,20) ,telemovel ="990257414",
            id_estrang_distrito = distrito.id, nomeDistrito = distrito.nome_distrito)
        pessoa.id = inserePesssoa(tabelaPessoas, pessoa)

        val tabelaTestes = TabelaTestes(db)
        val teste = Teste( temperatura = 36.5f, sintomas = "nenhum",estado_saude = "bom" ,data_teste= Date(2020,10,20),id_estrang_pessoas = pessoa.id)
        teste.id = insereTeste(tabelaTestes, teste)*/

        assertEquals(vacinas, getVacinasBaseDados(tabelaVacinas,vacinas.id))

        db.close()
    }*/






}