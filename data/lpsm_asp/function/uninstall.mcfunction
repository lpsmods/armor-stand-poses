# Made by: @Legopitstop
# Desc: Uninstalls this datapack
#
# Called By: Player

# Scoreboard(s)
scoreboard objectives remove lpsm_asp.util
scoreboard objectives remove lpsm_asp.lastPower
scoreboard objectives remove lpsm_asp.power

# Storage(s)
data remove storage lpsm_asp:poses poses
function lpsm_asp:cleanup
