# Made by: @Legopitstop
# Desc: Set the pose from the redstone power
#
# Called By: Public

# Get the pose id from power
$data modify storage lpsm_asp:poses find set value $(power)
execute store success score #bool lpsm_asp.util run function lpsm_asp:armor_stand/find_power
execute if score #bool lpsm_asp.util matches 0 run return run function lpsm_asp:api/from_id {id: "default"}

# Set from id
function lpsm_asp:api/from_id with storage lpsm_asp:poses pose
