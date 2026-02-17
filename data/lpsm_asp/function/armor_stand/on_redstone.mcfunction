# Made by: @Legopitstop
# Desc: When the redstone level changed since last tick. 
#
# Called By: lpsm_asp:armor_stand/redstone

execute store result storage lpsm_asp:poses temp.power int 1 run scoreboard players operation @s lpsm_asp.lastPower = @s lpsm_asp.power
function lpsm_asp:api/from_power with storage lpsm_asp:poses temp
