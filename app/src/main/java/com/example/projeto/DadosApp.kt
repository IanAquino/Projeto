package com.example.projeto

import androidx.fragment.app.Fragment
import com.example.projeto.classes.Pacientes
//import com.example.projeto.NovoPacienteFragment

class DadosApp {


        companion object {
            lateinit var activity: MainActivity
            //lateinit var listaPacienteFragment: ListaPacientesFragment
            //lateinit var novoPacienteFragment: NovoPacienteFragment
            lateinit var fragment: Fragment
            var pacienteSelecionado : Pacientes? = null
    }
}