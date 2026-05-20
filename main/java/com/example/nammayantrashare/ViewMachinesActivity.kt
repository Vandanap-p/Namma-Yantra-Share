package com.example.nammayantrashare

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nammayantrashare.adapters.MachineAdapter
import com.example.nammayantrashare.models.Machine
import com.google.firebase.firestore.FirebaseFirestore

class ViewMachinesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MachineAdapter

    private lateinit var etSearch: EditText

    private val machineList =
        ArrayList<Machine>()

    private val originalList =
        ArrayList<Machine>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        setContentView(
            R.layout.activity_view_machines
        )

        recyclerView =
            findViewById(
                R.id.recyclerView
            )

        etSearch =
            findViewById(
                R.id.etSearch
            )

        recyclerView.layoutManager =
            LinearLayoutManager(
                this
            )

        adapter =
            MachineAdapter(
                this,
                machineList
            )

        recyclerView.adapter =
            adapter

        loadMachines()

        etSearch.addTextChangedListener(

            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    filterMachines(
                        s.toString()
                    )
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {}
            }
        )
    }

    private fun loadMachines() {

        FirebaseFirestore
            .getInstance()
            .collection(
                "machines"
            )
            .addSnapshotListener {

                    value,
                    _ ->

                machineList.clear()

                originalList.clear()

                value?.documents?.forEach {

                    val machine =
                        it.toObject(
                            Machine::class.java
                        )

                    if(
                        machine != null
                    ){

                        machineList.add(
                            machine
                        )

                        originalList.add(
                            machine
                        )
                    }
                }

                adapter.notifyDataSetChanged()
            }
    }

    private fun filterMachines(
        query: String
    ) {

        machineList.clear()

        if(
            query.isEmpty()
        ){

            machineList.addAll(
                originalList
            )

        } else {

            originalList.forEach {

                if(

                    it.machineName.contains(
                        query,
                        true
                    )

                    ||

                    it.machineType.contains(
                        query,
                        true
                    )

                    ||

                    it.address.contains(
                        query,
                        true
                    )

                ){

                    machineList.add(
                        it
                    )
                }
            }
        }

        adapter.notifyDataSetChanged()
    }
}