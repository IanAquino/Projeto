package com.example.projeto

class DadosApp {


        companion object {
            lateinit var activity: MainActivity
            lateinit var listaPacienteFragment: ListaPacientesFragment
            lateinit var novoPacienteFragment: NovoPacienteFragment

            var pacienteSelecionado : Pacientes? = null
    }
}