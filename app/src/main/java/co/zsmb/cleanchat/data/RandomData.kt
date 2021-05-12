package co.zsmb.cleanchat.data

import kotlin.random.Random
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

fun randomMessageList(size: Int = Random.nextInt(4, 10)): List<Message> {
    return List(size) { randomMessage() }
}

fun randomMessage(): Message {
    val messages = listOf(
        "Cake or pie? I can tell a lot about you by which one you pick. It may seem silly, but cake people and pie people are really different. I know which one I hope you are, but that's not for me to decide. So, what is it? Cake or pie?",
        "The young man wanted a role model. He looked long and hard in his youth, but that role model never materialized. His only choice was to embrace all the people in his life he didn't want to be like.",
        "The headphones were on. They had been utilized on purpose. She could hear her mom yelling in the background, but couldn't make out exactly what the yelling was about. That was exactly why she had put them on. She knew her mom would enter her room at any minute, and she could pretend that she hadn't heard any of the previous yelling.",
        "She had been an angel for coming up on 10 years and in all that time nobody had told her this was possible. The fact that it could ever happen never even entered her mind. Yet there she stood, with the undeniable evidence sitting on the ground before her. Angels could lose their wings.",
        "It went through such rapid contortions that the little bear was forced to change his hold on it so many times he became confused in the darkness, and could not, for the life of him, tell whether he held the sheep right side up, or upside down.",
        "Sometimes there isn't a good answer. No matter how you try to rationalize the outcome, it doesn't make sense. And instead of an answer, you are simply left with a question. Why?",
        "It was their first date and she had been looking forward to it the entire week. She had her eyes on him for months, and it had taken a convoluted scheme with several friends to make it happen, but he'd finally taken the hint and asked her out. After all the time and effort she'd invested into it, she never thought that it would be anything but wonderful.",
        "It wasn't quite yet time to panic. There was still time to salvage the situation. At least that is what she was telling himself. The reality was that it was time to panic and there wasn't time to salvage the situation, but he continued to delude himself into believing there was.",
        "The lone lamp post of the one-street town flickered, not quite dead but definitely on its way out. Suitcase by her side, she paid no heed to the light, the street or the town. A car was coming down the street and with her arm outstretched and thumb in the air, she had a plan.",
    )
    val imageIds = listOf(
        null,
        null,
        null,
        null,
        null,
        null,
        "1620504395830-1d1c57f46151",
        "1618340338709-027f57b98a16",
        "1619732514485-2f6896f9e34c",
        "1581503039137-1cc062a9097b",
        "1619229725896-1b2ca516a6d8",
        "1619963168631-f9dcc897e348",
    )
    return Message(
        user = randomUser(),
        text = messages.random(),
        imageUrl = imageIds.random()
            ?.let { id -> "https://images.unsplash.com/photo-${id}?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=800&ixlib=rb-1.2.1&q=80&w=1200" },
    )
}

fun randomUserList(size: Int = 30): List<User> {
    return List(size) { randomUser() }
        .sortedByDescending { it.lastOnline }
        .sortedByDescending { it.isOnline }
}

fun randomUser(): User {
    val names = listOf(
        "Marc",
        "Sandy",
        "Lucie",
        "Laura",
        "Pierre",
        "Fanny",
        "Celine",
        "Alice",
        "Martin",
        "Pauline"
    )
    return User(
        name = names.random(),
        avatarUrl = randomAvatarUrl(),
        isOnline = Random.nextFloat() < 0.4,
        lastOnline = Date(
            Random.nextLong(
                minTime.toEpochMilli(),
                maxTime.toEpochMilli()
            )
        )
    )
}

private val maxTime = Instant.now()
private val minTime = maxTime.minus(2, ChronoUnit.DAYS)

fun randomAvatarUrl(): String {
    val category = listOf("men", "women").random()
    val index = (0..99).random()
    return "https://randomuser.me/api/portraits/$category/$index.jpg"
}

