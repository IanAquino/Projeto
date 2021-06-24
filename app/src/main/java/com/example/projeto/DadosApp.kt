package com.example.projeto

class DadosApp {


        companion object {
            lateinit var activity: MainActivity
            var listaPacienteFragment: ListaPacienteFragment? = null
            var novoPacienteFragment: NovoPacienteFragment? = null

            var pacienteSelecionado : paciente? = null
    }
}