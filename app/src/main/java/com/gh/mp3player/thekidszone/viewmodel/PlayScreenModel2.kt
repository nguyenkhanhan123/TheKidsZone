package com.gh.mp3player.thekidszone.viewmodel

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class PlayScreenModel2(
    var question: MutableLiveData<String> = MutableLiveData<String>().apply { value = "" },
    var ansA: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 },
    var ansB: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 },
    var ansC: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 },
    var time: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 90 },
    var score: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 },
    var best: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 },
) : BaseViewModel() {

    fun setValue(newValue: Int) {
        time.value = newValue
    }
    var answer: Int = 0

    fun initQuestion() {
        val random = Random()
        var a = random.nextInt(19) + 1
        var b = random.nextInt(19) + 1
        var sign = random.nextInt(12)
        var dau: Char = if (sign < 4) '+' else if (sign < 8) '-' else 'x'
        var s: String = "$a $dau $b = ?"
        question.postValue(s)
        answer = if (sign < 4) a + b else if (sign < 8) a - b else a * b
        var wrong1 = answer + random.nextInt(50) + 1
        var wrong2 = answer - random.nextInt(50) + 1
        if (wrong2 == wrong1) {
            wrong2 += 24
        }
        if (wrong2 == answer) {
            wrong2 += 24
        }
        var list: List<Int> = listOf(answer, wrong1, wrong2)
        Collections.shuffle(list)
        ansA.postValue(list[0])
        ansB.postValue(list[1])
        ansC.postValue(list[2])
    }

    fun check(string: String): Boolean {
        val int = string.toInt()
        if (int == answer) {
            if (score.value == null) return false
            score.postValue(score.value?.plus(1) ?: 0)
            initQuestion()
            return true
        }
        return false
    }

    fun startCoutDown() {
        GlobalScope.launch(Dispatchers.IO) {
            for (i in 1..90) {
                if (time.value == 0) {
                    break
                }
                Thread.sleep(700)
                GlobalScope.launch(Dispatchers.Main) {
                    val bl = (time.value ?: 0) >= 1
                    if (bl) {
                        time.postValue(time.value?.minus(1) ?: 0)
                    }
                }

            }
        }
    }

    fun gameOver() {
        time.postValue(0)
    }

    fun playAgain() {
        score.postValue(0)
        setValue(90)
        initQuestion()
        startCoutDown()

    }
}