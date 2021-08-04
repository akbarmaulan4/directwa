package com.lula.dirwa

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hbb20.CountryCodePicker
import java.net.URLEncoder

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var countryCode:String?="62"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_dirwa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var edtPhone = view.findViewById<EditText>(R.id.edtPhone);
        var edtMessage = view.findViewById<EditText>(R.id.edtMessage);
        var ccp = view.findViewById<CountryCodePicker>(R.id.ccp);

        ccp.setDefaultCountryUsingNameCode("ID");
        ccp.resetToDefaultCountry();
        ccp.setOnCountryChangeListener {
            countryCode=ccp!!.selectedCountryCode
            Toast.makeText(activity,"Country Code "+countryCode,Toast.LENGTH_SHORT).show()
        }

        edtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s?.length!! > 0){
                    val sda = s?.get(0);
                    if (s?.get(0).toString().equals("0")) {
                        edtPhone.setText("")
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        

        view.findViewById<Button>(R.id.btnSend).setOnClickListener{
            if(edtPhone.text.isEmpty()){
                Toast.makeText(activity, "No Handphone tidak boleh kosong", Toast.LENGTH_LONG).show();
            }else if(edtMessage.text.isEmpty()){
                Toast.makeText(activity, "Isi pesan tidak boleh kosong", Toast.LENGTH_LONG).show();
            }else{
                val packageManager : PackageManager
                val i = Intent(Intent.ACTION_VIEW)
                val url = "https://api.whatsapp.com/send?phone=" + countryCode+edtPhone.text.toString() + "&text="+ URLEncoder.encode(
                    edtMessage.text.toString(),
                    "UTF-8"
                )
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                if(activity?.let { it1 -> i.resolveActivity(it1.packageManager) } != null){
                    startActivity(i)
                }
            }
        }
    }
}