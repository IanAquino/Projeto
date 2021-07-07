package com.example.projeto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.projeto.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.example.projeto.NovoPacienteFragment


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu
    var menuAtual = R.menu.menu_lista_pacientes




        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_lista_pacientes, menu)
        this.menu = menu
        atualizaMenuListaPacientes(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val opcaoProcessada = when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                true
            }

            else -> when (menuAtual){
                R.menu.menu_lista_pacientes -> (DadosApp.fragment as ListaPacientesFragment).processaOpcaoMenu(item)
                R.menu.menu_novo_paciente -> (DadosApp.fragment as NovoPacienteFragment).processaOpcaoMenu(item)
               // R.menu.menu_edita_paciente-> (DadosApp.fragment as EditaPacienteFragment).processaOpcaoMenu(item)
              //  R.menu.menu_elimina_paciente -> (DadosApp.fragment as EliminaPacienteFragment).processaOpcaoMenu(item)

                R.menu.menu_lista_pacientes ->(DadosApp.fragment as ListaPacientesFragment).processaOpcaoMenu(item)
                else -> false

            }
        }
        return if(opcaoProcessada) true else super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaPacientes(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_paciente).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_paciente).setVisible(mostraBotoesAlterarEliminar)
    }
    fun enviar(view: View) {


    }
}