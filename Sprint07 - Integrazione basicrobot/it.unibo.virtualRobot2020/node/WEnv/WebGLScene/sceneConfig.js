const config = {
    floor: {
        size: { x: 30, y: 24                   }
    },
    player: {
        //position: { x: 0.5, y: 0.5 },		//CENTER
        position: { x: 0.12, y: 0.16 },		//INIT
        //position: { x: 0.8, y: 0.85 },		//END
        speed: 0.2
    },
     sonars: [
//        {
//            name: "sonar1",
//            position: { x: 0.99, y: 0.24 },
//            senseAxis: { x: true, y: false }
//        },
//        {
//            name: "sonar2",
//            position: { x: 0.99, y: 0.93},
//            senseAxis: { x: true, y: false }
//        } 
     ],
       movingObstacles: [
        // {
            // name: "ostacolo1",
            // position: { x: 0, y: 0.68 },
            // directionAxis: { x: true, y: false },
            // speed: 0.02,
            // range: 28
        // },
        // {
            // name: "ostacolo2",
            // position: { x: .42, y: .01 },
            // directionAxis: { x: false, y: true },
            // speed: 0.05,
            // range: 25
        // }
     ],
   staticObstacles: [

		{
            name: "fridge",
            centerPosition: { x: 0.85, y: 1.0},
            size: { x: 0.24, y: 0.07}
        },

        {
            name: "dishwasher",
            centerPosition: { x: 0.85, y: 0.0},
            size: { x: 0.2, y: 0.045        }
		},
        {
            name: "pantry",
            centerPosition: { x: 0.18, y: 0.0},
            size: { x: 0.2, y: 0.045        }
		},
		
        {
            name: "table",
            centerPosition: { x: 0.60, y: 0.40},
            size: { x: 0.16, y: 0.14      }
		},


        {
            name: "wallUp",
            centerPosition: { x: 0.48, y: 0.97},
            size: { x: 0.89, y: 0.01}
        },
        {
            name: "wallDown",
            centerPosition: { x: 0.45, y: 0.01},
            size: { x: 0.85, y: 0.01}
        },
        {
            name: "wallLeft",
            centerPosition: { x: 0.02, y: 0.45},
            size: { x: 0.01, y: 0.88}
        },
        {
            name: "wallRight",
            centerPosition: { x: 0.97, y: 0.5},
            size: { x: 0.01, y: 0.99}
        }
    ]
}

export default config;
