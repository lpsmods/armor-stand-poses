# Made by: @Legopitstop
# Desc: Find the pose using the ID
#
# Called By: lpsm_asp:armor_stand/update

# Loop through all poses
execute store result score #len lpsm_asp.util run data get storage lpsm_asp:poses poses
execute if score #len lpsm_asp.util matches 1.. run function lpsm_asp:armor_stand/find_power_loop

execute unless data storage lpsm_asp:poses pose run return fail
return 1
