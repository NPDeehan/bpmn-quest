// e.g.: getFootprints('7f61ee67-4be0-11e5-9a7c-843220524153');


function getFootprints(ActivityId) {
	var ActivityServer = 'http://localhost:8080/engine-rest/history/activity-instance?processInstanceId=';
	var footprints = [];
	var restCall = $.getJSON( ActivityServer + ActivityId , function() {
		for(var i = 0; i < restCall.length; i++) {
			var obj = restCall[i];
			footprints.push(obj.activityId);
		}
		console.log(footprints);
	}).fail(function() {
		console.log( "footprints error" );
	}).always(function() {
		console.log( "footprints complete" );
	});
	return footprints;
}

