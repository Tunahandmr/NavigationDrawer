package com.tunahan.navigationdrawer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.tunahan.navigationdrawer.databinding.FragmentFirstBinding


class FirstFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val action = ActionBarDrawerToggle(activity,binding.drawerLayout,binding.materialToolbar,0,0)
        binding.drawerLayout.addDrawerListener(action)
        action.drawerArrowDrawable.color = resources.getColor(R.color.white,activity?.theme)
        action.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)


    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {


        when(item.itemId){


            R.id.goGoogle -> {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"))
                startActivity(intent)

            }

            R.id.goSecondFragment -> {

                val a = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
                view?.let { Navigation.findNavController(it).navigate(a) }
            }

            R.id.shareApp -> {



                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val body:String = "Google uygulamasını indirmek için:https://play.google.com/store/apps/details?id=com.google.android.googlequicksearchbox&hl=tr&gl=US"
                val head:String = "Google App"
                intent.putExtra(Intent.EXTRA_SUBJECT,head)
                intent.putExtra(Intent.EXTRA_TEXT,body)
                startActivity(Intent.createChooser(intent,"share using"))


            }

            R.id.feedback ->{

                val b:String = "body"
                val i = Intent(Intent.ACTION_SENDTO)
                val uriText:String = "mailto:"+Uri.encode("android@gmail.com")+"?subject="
                val uri:Uri = Uri.parse(uriText)
                i.data = uri
                startActivity(Intent.createChooser(i,"send email"))

            }


            R.id.exit ->{
                val alert = AlertDialog.Builder(requireActivity())
                alert.setTitle("Exit")
                alert.setMessage("Are you sure?")

                alert.setPositiveButton("Yes"){ d, w->
                    activity?.finish()
                }

                alert.setNegativeButton("No"){d,w->
                    Toast.makeText(activity?.applicationContext,"No exit", Toast.LENGTH_LONG).show()
                    binding.drawerLayout.close()
                }

                alert.show()

            }


        }


        return true
    }


    //back button press
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {

                    binding.drawerLayout.close()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}