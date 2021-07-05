package com.example.projeto

class DadosApp {


        companion object {
            lateinit var activity: MainActivity
            var listaPacienteFragment: ListaPacientesFragment? = null
            var novoPacienteFragment: NovoPacienteFragment? = null

            var pacienteSelecionado : Pacientes? = null
    }
}