# Made by: @Legopitstop
# Desc: Find the ID in poses
#
# Called By: lpsm_asp:armor_stand/find_id


# Copy
data modify storage lpsm_asp:poses temp set from storage lpsm_asp:poses poses[0]

# Match
scoreboard players set #bool lpsm_asp.util 0
execute store success score #bool lpsm_asp.util run data modify storage lpsm_asp:poses temp.id set from storage lpsm_asp:poses find
execute if score #bool lpsm_asp.util matches 0 run return run data modify storage lpsm_asp:poses pose set from storage lpsm_asp:poses temp

data modify storage lpsm_asp:poses poses append from storage lpsm_asp:poses poses[0]
data remove storage lpsm_asp:poses poses[0]

scoreboard players remove #len lpsm_asp.util 1
execute if score #len lpsm_asp.util matches 1.. run function lpsm_asp:armor_stand/find_id_loop
