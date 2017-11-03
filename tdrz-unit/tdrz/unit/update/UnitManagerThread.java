package tdrz.unit.update;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tdrz.server.RawApiData;
import tdrz.server.RawApiDataType;
import tdrz.unit.update.handler.airbase.ApiAirbaseChangeName;
import tdrz.unit.update.handler.airbase.ApiAirbaseExpand;
import tdrz.unit.update.handler.airbase.ApiAirbaseInformation;
import tdrz.unit.update.handler.airbase.ApiAirbaseSetPlane;
import tdrz.unit.update.handler.airbase.ApiAirbaseSupply;
import tdrz.unit.update.handler.deck.ApiDeck;
import tdrz.unit.update.handler.deck.ApiDeckChange;
import tdrz.unit.update.handler.deck.ApiDeckItemuseCond;
import tdrz.unit.update.handler.deck.ApiDeckPresetDeck;
import tdrz.unit.update.handler.deck.ApiDeckPresetDelete;
import tdrz.unit.update.handler.deck.ApiDeckPresetRegister;
import tdrz.unit.update.handler.deck.ApiDeckPresetSelect;
import tdrz.unit.update.handler.deck.ApiDeckShipLock;
import tdrz.unit.update.handler.deck.ApiDeckUpdateDeckName;
import tdrz.unit.update.handler.kaisou.ApiKaisouMarriage;
import tdrz.unit.update.handler.kaisou.ApiKaisouOpenExSlot;
import tdrz.unit.update.handler.kaisou.ApiKaisouPowerUp;
import tdrz.unit.update.handler.kaisou.ApiKaisouRemodeling;
import tdrz.unit.update.handler.kaisou.ApiKaisouShip3;
import tdrz.unit.update.handler.kaisou.ApiKaisouSlotDeprive;
import tdrz.unit.update.handler.kaisou.ApiKaisouSlotExchange;
import tdrz.unit.update.handler.kaisou.ApiKaisouSlotItemLock;
import tdrz.unit.update.handler.kaisou.ApiKaisouSlotSet;
import tdrz.unit.update.handler.kaisou.ApiKaisouSlotSetEx;
import tdrz.unit.update.handler.kaisou.ApiKaisouUnsetSlotAll;
import tdrz.unit.update.handler.kdock.ApiKdock;
import tdrz.unit.update.handler.kdock.ApiKdockCreateShip;
import tdrz.unit.update.handler.kdock.ApiKdockGetShip;
import tdrz.unit.update.handler.kdock.ApiKdockSpeedChange;
import tdrz.unit.update.handler.mission.ApiMission;
import tdrz.unit.update.handler.mission.ApiMissionResult;
import tdrz.unit.update.handler.mission.ApiMissionReturn;
import tdrz.unit.update.handler.mission.ApiMissionStart;
import tdrz.unit.update.handler.ndock.ApiNdock;
import tdrz.unit.update.handler.ndock.ApiNdockNyukyoSpeedChange;
import tdrz.unit.update.handler.ndock.ApiNdockNyukyoStart;
import tdrz.unit.update.handler.other.ApiBasic;
import tdrz.unit.update.handler.other.ApiCharge;
import tdrz.unit.update.handler.other.ApiCombined;
import tdrz.unit.update.handler.other.ApiCreateSlotItem;
import tdrz.unit.update.handler.other.ApiDestroyShip;
import tdrz.unit.update.handler.other.ApiDestroySlotItem;
import tdrz.unit.update.handler.other.ApiEventMapRankSelect;
import tdrz.unit.update.handler.other.ApiGetIncentive;
import tdrz.unit.update.handler.other.ApiItemUse;
import tdrz.unit.update.handler.other.ApiMapInfo;
import tdrz.unit.update.handler.other.ApiMasterData;
import tdrz.unit.update.handler.other.ApiMaterial;
import tdrz.unit.update.handler.other.ApiPort;
import tdrz.unit.update.handler.other.ApiRecord;
import tdrz.unit.update.handler.other.ApiRequireInfo;
import tdrz.unit.update.handler.other.ApiShip2;
import tdrz.unit.update.handler.other.ApiSlotItem;
import tdrz.unit.update.handler.other.ApiSortieConditions;
import tdrz.unit.update.handler.other.ApiUpdateComment;
import tdrz.unit.update.handler.other.ApiUseItem;
import tdrz.unit.update.handler.practice.ApiPracticeChangeMatchingKind;
import tdrz.unit.update.handler.practice.ApiPracticeEnemyInfo;
import tdrz.unit.update.handler.practice.ApiPracticeList;
import tdrz.unit.update.handler.quest.ApiQuestClear;
import tdrz.unit.update.handler.quest.ApiQuestList;
import tdrz.unit.update.handler.quest.ApiQuestStart;
import tdrz.unit.update.handler.quest.ApiQuestStop;
import tdrz.unit.update.handler.remodel.ApiRemodelSlot;
import tdrz.unit.update.handler.remodel.ApiRemodelSlotList;
import tdrz.unit.update.handler.remodel.ApiRemodelSlotListDetail;

public class UnitManagerThread extends Thread {
	private final Logger LOG = LogManager.getLogger(this.getClass());
	private final long time;
	private final String serverName, uri;
	private final Map<String, String> headers;
	private final ByteArrayOutputStream requestBody, responseBody;

	public UnitManagerThread(long time, String serverName, String uri, Map<String, String> headers, ByteArrayOutputStream requestBody, ByteArrayOutputStream responseBody) {
		this.time = time;
		this.serverName = serverName;
		this.uri = uri;
		this.headers = headers;
		this.requestBody = requestBody;
		this.responseBody = responseBody;
	}

	@Override
	public void run() {
		RawApiData rawApiData;
		try {
			rawApiData = new RawApiData(this.time, this.serverName, this.uri, this.headers, this.requestBody, this.responseBody);
		} catch (Exception e) {
			this.LOG.warn(this.uri + "\r\n" + this.requestBody + "\r\n" + this.responseBody, e);
			return;
		}

		RawApiDataType type = rawApiData.getType();
		if (type == null) {
			System.err.println("Ｘ定义的api : " + rawApiData.getUri());
			return;
		}
		System.out.println("Ｏ定义的api : " + rawApiData.getUri());

		JsonObject json = rawApiData.getJsonObject();
		int api_result = json.getInt("api_result");
		if (api_result != 1) {//有猫
			return;
		}

		UnitManager unitManager = UnitManager.get();
		UnitHandler handler = this.getUnitHandler(type).getUnitHandler(unitManager, rawApiData.getTime(), rawApiData.getFields(), json.get("api_data"));
		if (handler.haveAnyUpdate()) {//对一些无需处理的 api ,直接滤过
			unitManager.acceptUnitHandler(handler);
		}
	}

	@FunctionalInterface
	private interface NewUnitHandler {
		public UnitHandler getUnitHandler(UnitManager unitManager, long time, Map<String, String> fields, JsonValue api_data);
	}

	public NewUnitHandler getUnitHandler(RawApiDataType type) {
		switch (type) {
			case KDOCK:
				return ApiKdock::new;
			case KDOCK_CREATESHIP:
				return ApiKdockCreateShip::new;
			case KDOCK_SPEEDCHANGE:
				return ApiKdockSpeedChange::new;
			case KDOCK_GETSHIP:
				return ApiKdockGetShip::new;

			case NDOCK:
				return ApiNdock::new;
			case NDOCK_NYUKYO_START:
				return ApiNdockNyukyoStart::new;
			case NDOCK_NYUKYO_SPEEDCHANGE:
				return ApiNdockNyukyoSpeedChange::new;

			case DECK:
				return ApiDeck::new;
			case DECK_CHANGE:
				return ApiDeckChange::new;
			case DECK_UPDATEDECKNAME:
				return ApiDeckUpdateDeckName::new;
			case DECK_SHIP_LOCK:
				return ApiDeckShipLock::new;
			case DECK_ITEMUSE_COND:
				return ApiDeckItemuseCond::new;
			case DECK_PRESET_DECK:
				return ApiDeckPresetDeck::new;
			case DECK_PRESET_SELECT:
				return ApiDeckPresetSelect::new;
			case DECK_PRESET_REGISTER:
				return ApiDeckPresetRegister::new;
			case DECK_PRESET_DELETE:
				return ApiDeckPresetDelete::new;

			case MISSION:
				return ApiMission::new;
			case MISSION_START:
				return ApiMissionStart::new;
			case MISSION_RETURN:
				return ApiMissionReturn::new;
			case MISSION_RESULT:
				return ApiMissionResult::new;

			case QUEST_LIST:
				return ApiQuestList::new;
			case QUEST_START:
				return ApiQuestStart::new;
			case QUEST_STOP:
				return ApiQuestStop::new;
			case QUEST_CLEAR:
				return ApiQuestClear::new;

			case REMODEL_SLOT:
				return ApiRemodelSlot::new;
			case REMODEL_SLOTLIST:
				return ApiRemodelSlotList::new;
			case REMODEL_SLOTLIST_DETAIL:
				return ApiRemodelSlotListDetail::new;

			case KAISOU_POWERUP:
				return ApiKaisouPowerUp::new;
			case KAISOU_SLOTITEM_LOCK:
				return ApiKaisouSlotItemLock::new;
			case KAISOU_SHIP3:
				return ApiKaisouShip3::new;
			case KAISOU_OPEN_EXSLOT:
				return ApiKaisouOpenExSlot::new;
			case KAISOU_SLOT_EXCHANGE:
				return ApiKaisouSlotExchange::new;
			case KAISOU_SLOT_DEPRIVE:
				return ApiKaisouSlotDeprive::new;
			case KAISOU_MARRIAGE:
				return ApiKaisouMarriage::new;
			case KAISOU_SLOTSET:
				return ApiKaisouSlotSet::new;
			case KAISOU_SLOTSET_EX:
				return ApiKaisouSlotSetEx::new;
			case KAISOU_UNSETSLOT_ALL:
				return ApiKaisouUnsetSlotAll::new;
			case KAISOU_REMODELING:
				return ApiKaisouRemodeling::new;

			case AIRBASE_CHANGENAME:
				return ApiAirbaseChangeName::new;
			case AIRBASE_SUPPLY:
				return ApiAirbaseSupply::new;
			case AIRBASE_EXPAND:
				return ApiAirbaseExpand::new;
			case AIRBASE_INFORMATION:
				return ApiAirbaseInformation::new;
			case AIRBASE_SETPLANE:
				return ApiAirbaseSetPlane::new;

			case PRACTICE_LIST:
				return ApiPracticeList::new;
			case PRACTICE_CHANGE_MATCHING_KIND:
				return ApiPracticeChangeMatchingKind::new;
			case PRACTICE_ENEMYINFO:
				return ApiPracticeEnemyInfo::new;

			case CHARGE:
				return ApiCharge::new;
			case CREATESLOTITEM:
				return ApiCreateSlotItem::new;
			case DESTROYSHIP:
				return ApiDestroyShip::new;
			case DESTROYSLOTITEM:
				return ApiDestroySlotItem::new;

			case COMBINED:
				return ApiCombined::new;
			case USEITEM:
				return ApiUseItem::new;
			case ITEMUSE:
				return ApiItemUse::new;
			case MASTERDATA:
				return ApiMasterData::new;
			case REQUIRE_INFO:
				return ApiRequireInfo::new;
			case MAPINFO:
				return ApiMapInfo::new;
			case MATERIAL:
				return ApiMaterial::new;
			case PORT:
				return ApiPort::new;
			case BASIC:
				return ApiBasic::new;
			case SLOT_ITEM:
				return ApiSlotItem::new;
			case SHIP2:
				return ApiShip2::new;
			case UPDATECOMMENT:
				return ApiUpdateComment::new;
			case EVENTMAP_RANK_SELECT:
				return ApiEventMapRankSelect::new;
			case SORTIE_CONDITIONS:
				return ApiSortieConditions::new;
			case RECORD:
				return ApiRecord::new;
			case GET_INCENTIVE:
				return ApiGetIncentive::new;

			case UNSETSLOT:
			case MXLTVKPYUKLH:
			case PAYCHECK:
			case PAYITEM:
			case PAYITEM_USE:
			case PICTURE_BOOK:
			case MUSIC_LIST:
			case MUSIC_PLAY:
			case RADIO_PLAY:
			case SET_PORTBGM:
			case FURNITURE_BUY:
			case FURNITURE_CHANGE:
				break;

			/*------------------------------------------------------ 战斗部分 --------------------------------------------------------------------------------------------------------*/

			case BATTLE_PRACTICE_DAY:
			case BATTLE_PRACTICE_MIDNIGHT:
			case BATTLE_PRACTICE_RESULT:

			case BATTLE_SHIPDECK:
			case BATTLE_GOBACK_PORT:
			case BATTLE_START_AIR_BASE:

			case BATTLE_START:
			case BATTLE_NEXT:

			case BATTLE_AIRBATTLE:
			case BATTLE_AIRBATTLE_LD:
			case BATTLE_DAY:
			case BATTLE_MIDNIGHT:
			case BATTLE_MIDNIGHT_SP:
			case BATTLE_RESULT:

			case COMBINEBATTLE_AIRBATTLE:
			case COMBINEBATTLE_AIRBATTLE_LD:
			case COMBINEBATTLE_MIDNIGHT_SP:
			case COMBINEBATTLE_RESULT:
			case COMBINEBATTLE_DAY:
			case COMBINEBATTLE_DAY_WATER:
			case COMBINEBATTLE_MIDNIGHT:
			case COMBINEBATTLE_EC_DAY:
			case COMBINEBATTLE_EACH_DAY:
			case COMBINEBATTLE_EACH_DAY_WATER:
			case COMBINEBATTLE_EC_MIDNIGHT:
		}
		return (unitManager, time, fields, api_data) -> UnitHandler.EMPTY_HANDLER;
	}
}
