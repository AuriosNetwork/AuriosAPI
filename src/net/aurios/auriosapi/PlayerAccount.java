package net.aurios.auriosapi;

import net.aurios.auriosapi.Enums.MathOperations;

public class PlayerAccount {
	
	AuriosAPI core;
	public PlayerAccount(AuriosAPI core) {
		this.core = core;
	}
	
	public boolean playerExists(String uuid) {
		return core.getMySQL().get("UUID", "playerdata", "UUID", uuid) != null;
	}
	
	public int currentBalance(String uuid) {
		if(playerExists(uuid)) {
			return (int) core.getMySQL().get("Balance", "playerdata", "UUID", uuid);
		}
		return 0;
	}
	
	public void setBalance(String uuid, Enums.MathOperations mathOperation, int amount) {
		if(playerExists(uuid)) {
			if(mathOperation != null) {
				int newBalance = currentBalance(uuid);
				if(mathOperation == MathOperations.ADDITION) {
					newBalance += amount;
				}else if(mathOperation == MathOperations.SUBTRACTION) {
					newBalance -= amount;
				}
				if(newBalance >= 0) {
					core.getMySQL().set("playerdata", "Balance", newBalance, "UUID", uuid);
				}
			}
		}
	}

}
