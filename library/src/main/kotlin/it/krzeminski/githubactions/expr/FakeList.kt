package it.krzeminski.githubactions.expr

internal class FakeList(val name: String) : List<String> by emptyList() {
    override fun get(index: Int): String = "$name[$index]"
}
