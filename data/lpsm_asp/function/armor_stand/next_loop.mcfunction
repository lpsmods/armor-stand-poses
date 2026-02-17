# Made by: @Legopitstop
# Desc: Find the next pose
#
# Called By: lpsm_asp:armor_stand/next

# Copy
data modify storage lpsm_asp:poses temp.match set from storage lpsm_asp:poses temp.id

# Match
scoreboard players set #bool lpsm_asp.util 0
execute store success score #bool lpsm_asp.util run data modify storage lpsm_asp:poses temp.match set from storage lpsm_asp:poses poses[0].id
execute if score #bool lpsm_asp.util matches 0 run return run data modify storage lpsm_asp:poses pose set from storage lpsm_asp:poses poses[1]

data modify storage lpsm_asp:poses poses append from storage lpsm_asp:poses poses[0]
data remove storage lpsm_asp:poses poses[0]

scoreboard players remove #len lpsm_asp.util 1
execute if score #len lpsm_asp.util matches 1.. run function lpsm_asp:armor_stand/next_loop
