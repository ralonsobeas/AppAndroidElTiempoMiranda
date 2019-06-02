package com.example.myapplication.Fragments

import android.content.Context
import android.content.res.Resources
import android.gesture.GestureUtils
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.Data

import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_fragment_weather.*
import kotlinx.android.synthetic.main.fragment_fragment_weather.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val DESC = "desc"
private const val TEMP = "temp"
private const val IMG = "img"
private const val DIA = "dia"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentWeather.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentWeather.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentWeather : Fragment() {
    // TODO: Rename and change types of parameters
    private var desc: String? = null
    private var temp: String? = null
    private var img: String? = null
    private var dia: String? = null



    private var listener: OnFragmentInteractionListener? = null
    private var rootView: View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            desc = it.getString(DESC)
            temp = it.getString(TEMP)
            img = it.getString(IMG)
            dia = it.getString(DIA)

        }


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

    //Recibir los datos de la api del día indicado

        rootView =inflater.inflate(R.layout.fragment_fragment_weather, container, false)
        rootView!!.tvDia.text=dia
        var strtemp = "$temp ºC"
        rootView!!.tvTemp.text =strtemp
        rootView!!.tvDesc.text=desc


        img=img?.replace('-','_',true)

        Log.d("IMAGE",img)


        rootView!!.imgTiempo.setImageResource(resources.getIdentifier("ic_$img","drawable", context?.packageName))
        // Inflate the layout for this fragment
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentWeather.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(desc: String, temp: String,img:String,dia:String) =
            FragmentWeather().apply {
                arguments = Bundle().apply {
                    putString(DESC, desc)
                    putString(TEMP, temp)
                    putString(IMG, img)
                    putString(DIA, dia)
                }
            }
    }
}
