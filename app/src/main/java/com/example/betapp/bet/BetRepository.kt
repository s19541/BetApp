package com.example.betapp.bet

import androidx.lifecycle.MutableLiveData
import com.example.betapp.game.Game
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class BetRepository(private val fbdb: FirebaseDatabase) {

    val allBets: MutableLiveData<HashMap<String, Bet>> =
        MutableLiveData<HashMap<String, Bet>>().also{
            it.value = HashMap<String, Bet>()
        }

    init{
        fbdb.getReference("Bet").addChildEventListener(
            object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val bet = Bet(
                        id = snapshot.ref.key as String,
                        gameId = snapshot.child("game").getValue(String::class.java)!!,
                        rate = snapshot.child("price").getValue(Double::class.java)!!,
                        result = snapshot.child("result").getValue(Result::class.java)!!,
                        input = snapshot.child("input").getValue(Double::class.java)!!,
                        settled = snapshot.child("settled").getValue(Boolean::class.java)!!,
                    )
                    allBets.value?.put(bet.id, bet)
                    allBets.postValue(allBets.value)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val bet = Bet(
                        id = snapshot.ref.key as String,
                        gameId = snapshot.child("game").getValue(String::class.java)!!,
                        rate = snapshot.child("price").getValue(Double::class.java)!!,
                        result = snapshot.child("result").getValue(Result::class.java)!!,
                        input = snapshot.child("input").getValue(Double::class.java)!!,
                        settled = snapshot.child("settled").getValue(Boolean::class.java)!!,
                    )
                    allBets.value?.set(bet.id, bet)
                    allBets.postValue(allBets.value)
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    allBets.value?.remove(snapshot.ref.key)
                    allBets.postValue(allBets.value)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }
        )
    }

    fun insert(bet: Bet){
        fbdb.getReference("Bet").push().also{
            bet.id = it.ref.key.toString()
            it.setValue(bet)
        }
    }

}