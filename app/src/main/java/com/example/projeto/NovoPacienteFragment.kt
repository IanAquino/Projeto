package com.example.projeto

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.projeto.*
import com.google.android.material.snackbar.Snackbar
import com.example.projeto.tabelas.TabelaPacientes
import com.example.projeto.classes.Pacientes
import com.example.projeto.tabelas.TabelaVacinas
import com.example.projeto.classes.Vacinas
import com.example.projeto.tabelas.TabelaMarcacoes
import com.example.projeto.classes.Marcacoes
import java.util.*

class NovoPacienteFragment : Fragment(), LoaderManager.LoaderCallbacks <Cursor> {


    private lateinit var galleryViewModel: NovoPacienteViewModel

    private lateinit var editTextNome: EditText
    private lateinit var editTextNascimento: EditText
    private lateinit var editTextContacto: EditText
    private lateinit var editTextNif: EditText
    private lateinit var editTextMorada: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(NovoPacienteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_novo_paciente, container, false)
        // val textView: TextView = root.findViewById(R.id.textView)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            //  textView.text = it
        })
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_novo_paciente
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNome = view.findViewById(R.id.editTextNome)
        editTextNascimento = view.findViewById(R.id.editTextNascimento)
        editTextContacto = view.findViewById(R.id.editTextContacto)
        editTextNif = view.findViewById(R.id.editTextNif)
        editTextMorada = view.findViewById(R.id.editTextMorada)


        LoaderManager.getInstance(this)
            .initLoader(ID_PACIENTES,null, this)


    }

    fun navegaListaPacientes(){
        findNavController().navigate(R.id.action_NovoPacienteFragment_to_ListaPacientesFragment)
    }

    fun guardar(){

        val nome = editTextNome.text.toString()
        if(nome.isEmpty()){
            editTextNome.setError(getString(R.string.preencha_nome))
            editTextNome.requestFocus()
            return
        }

        val nascimento = editTextNascimento.text.toString()
        if(nascimento.isEmpty()){
            editTextNascimento.setError(getString(R.string.preencha_nascimento))
            editTextNascimento.requestFocus()
            return
        }

        val contacto = editTextContacto.text.toString()
        if(contacto == null){
            editTextContacto.setError(getString(R.string.preencha_contacto))
            editTextContacto.requestFocus()
            return
        }

        val nif = editTextNif.text.toString()
        if(nif.isEmpty()){
            editTextNif.setError(getString(R.string.preencha_nif))
            editTextNif.requestFocus()
            return
        }
        val morada = editTextMorada.text.toString()
        if(nif.isEmpty()){
            editTextMorada.setError(getString(R.string.preencha_morada))
            editTextMorada.requestFocus()
            return
        }

        val pacientes = Pacientes(nome = nome, nascimento = Date(nascimento), contacto = contacto, nif = nif, morada = "Rua 21")
        val uri = activity?.contentResolver?.insert(
            ContentProviderVacinas.ENDERECO_PACIENTES, pacientes.toContentValues()
        )
        if(uri == null){
            Snackbar.make(editTextNome,
                getString(R.string.erro_ao_inserir_paciente),
                Snackbar.LENGTH_LONG).show()
            return
        }


        navegaListaPacientes()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.action_guardar_novo_paciente -> guardar()
            R.id.action_cancelar_novo_paciente -> navegaListaPacientes()

            else -> return false
        }
        return true
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ContentProviderVacinas.ENDERECO_PACIENTES,
            TabelaPacientes.TODAS_COLUNAS,
            null, null,
            TabelaPacientes.CAMPO_NOME

        )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */




    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */


    companion object{
        const val ID_PACIENTES = 0
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

}
