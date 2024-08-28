package player.domain.model

enum class PlayerLevel(val indicator: Int, val description: String) {
    PRESIDENT(1, "Presidente"),
    BOARD(2, "Board"),
    MONTHLY(3, "Mensalista"),
    SPOT(4, "Spot"),
    PENDING(5, "Pendente"),
    REJECTED(6, "Recusado")
}
